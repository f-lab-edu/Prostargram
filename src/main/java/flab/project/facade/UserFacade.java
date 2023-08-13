package flab.project.facade;

import static flab.project.data.enums.FileType.PROFILE_IMAGE;

import flab.project.ObjectStorage;
import flab.project.config.Filtering.BadWordChecker;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InputBadWordException;
import flab.project.data.InterestsDelta;
import flab.project.data.SocialAccountsDelta;
import flab.project.data.dto.model.Profile;
import flab.project.data.file.ProfileImgage;
import flab.project.service.HashtagService;
import flab.project.service.InterestService;
import flab.project.service.SocialAccountService;
import flab.project.service.UserService;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserService userService;
    private final SocialAccountService soicalAccountService;
    private final InterestService interestService;
    private final HashtagService hashtagService;

    private final ObjectStorage objectStorage;
    private final BadWordChecker badWordChecker;

    @Transactional
    public SuccessResponse updateProfile(
        long userId,
        Profile updateProfileDto,
        MultipartFile profileImg
    ) {

        convertHtmlEscape(updateProfileDto);
        updateProfileDto.convertInterestsToLowerCase();

        filterBadWord(updateProfileDto);

        // todo 기존 img는 삭제하는 로직 필요함.
        if (Objects.nonNull(profileImg)) {
            String profileImageFileName = objectStorage.getFileNamesInBucket(userId).get(0);
            objectStorage.deleteProfileImage(profileImageFileName);
            String imgUrl = objectStorage.uploadFile(userId, profileImg, PROFILE_IMAGE);
            updateProfileDto.setProfileImg(imgUrl);
        }

        // Todo 매번 threadPool을 생성하는게 옳을까..?
        // 해당 API를 위한 Thread Pool을 따로 할당해놓는게 과연 옳을까?

        List<Runnable> updateProfileTasks = getUpdateProfileTasks(userId, updateProfileDto);

        ExecutorService updateProfileService = Executors.newFixedThreadPool(3);

        for (Runnable updateProfileTask : updateProfileTasks) {
            updateProfileService.submit(updateProfileTask);
        }

        return new SuccessResponse();
    }

    private void convertHtmlEscape(Profile updateProfile) {
        List<String> stringFields = updateProfile.getStringFields();

        stringFields.forEach(HtmlUtils::htmlEscape);
    }

    private List<Runnable> getUpdateProfileTasks(long userId, Profile updateProfileDto) {
        Runnable updateUserTableTask = () -> {
            userService.updateUserTable(userId, updateProfileDto);
        };

        Runnable updateSocialAccountsTask = () -> {
            SocialAccountsDelta socialAccountsDelta
                = soicalAccountService.getSocialAccountsDelta(userId,
                updateProfileDto.getSocialAccounts());
            soicalAccountService.updateSocialAccounts(userId, socialAccountsDelta);
        };

        Runnable updateInterestsTask = () -> {
            InterestsDelta interestsDelta
                = interestService.getInterestDelta(userId, updateProfileDto.getInterests());
            hashtagService.insertNonExistHashtag(interestsDelta);
            interestService.updateInterests(userId, interestsDelta);
        };

        return List.of(updateUserTableTask, updateSocialAccountsTask,
            updateInterestsTask);
    }

    private void filterBadWord(Profile updateProfileDto) {
        List<String> targetBadWordFilter = updateProfileDto.getStringFields();
        boolean hasBadWord = badWordChecker.hasBadWord(targetBadWordFilter);

        if (hasBadWord) {
            throw new InputBadWordException("욕설을 포함하고 있습니다.");
        }
    }

}

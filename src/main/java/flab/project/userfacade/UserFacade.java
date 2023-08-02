package flab.project.userfacade;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.InterestsDelta;
import flab.project.data.SocialAccountsDelta;
import flab.project.data.dto.model.Profile;
import flab.project.service.HashtagService;
import flab.project.service.InterestService;
import flab.project.service.SocialAccountService;
import flab.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final UserService userService;
    private final SocialAccountService soicalAccountService;
    private final InterestService interestService;
    private final HashtagService hashtagService;

    @Transactional
    public SuccessResponse updateProfile(long userId, Profile updateProfileDto) {
//         todo profileImgUrl이 아니라 S3(Naver)에 File Upload하는 형식으로 수정 필요.
        // Command를 만들고 Command에
        // Profile, SocialAccountDelta, InterestDelta 이런게 있어도 좋을거같긴한데..

        // todo selfIntroduction같은 경우는 실제로는 악성 코드가 없는지 검사 로직 돌려야함.
        // todo 전체적으로 쿼리가 굉장히 많이 필요함. 이거 비동기로 ok 반환하고 싶은데 가능한가?
//        checkValidation(updateProfileDto);

        //todo 해시태그, 관심사는 소문자만 가능하므로 Dto에 들어올 때,
        // 대문자 있으면 변환과정 or InvalidUserInput반환해야함.

        userService.updateUserTable(userId, updateProfileDto);

        SocialAccountsDelta socialAccountsDelta
            = soicalAccountService.getSocialAccountsDelta(userId, updateProfileDto.getSocialAccounts());
        soicalAccountService.updateSocialAccounts(userId, socialAccountsDelta);

        InterestsDelta interestsDelta
            = interestService.getInterestDelta(userId, updateProfileDto.getInterests());
        hashtagService.insertNonExistHashtag(interestsDelta);
        interestService.updateInterests(userId, interestsDelta);

        return new SuccessResponse();
    }
}

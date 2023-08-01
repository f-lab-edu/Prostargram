package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.InterestsDelta;
import flab.project.data.SocialAccountsDelta;
import flab.project.data.dto.model.Profile;
import flab.project.data.dto.User;
import flab.project.data.dto.model.Icon;
import flab.project.data.dto.model.SocialAccounts;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.mapper.HashtagMapper;
import flab.project.mapper.HyperLinkMapper;
import flab.project.mapper.IconMapper;
import flab.project.mapper.InterestMapper;
import flab.project.mapper.UserMapper;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashtagMapper hashtagMapper;
    private final HyperLinkMapper hyperLinkMapper;
    private final InterestMapper interestMapper;
    private final IconMapper iconMapper;

    public List<User> getFollows(Long userId, GetFollowsType requestType) {
        List<User> result = userMapper.findAll(requestType, userId);

        return result;
    }

    @Transactional
    public SuccessResponse updateProfile(long userId, Profile updateProfileDto) {
        try {
            //todo profileImgUrl이 아니라 S3(Naver)에 File Upload하는 형식으로 수정 필요.
            // Command를 만들고 Command에
            // Profile, SocialAccountDelta, InterestDelta 이런게 있어도 좋을거같긴한데..

            // todo selfIntroduction같은 경우는 실제로는 악성 코드가 없는지 검사 로직 돌려야함.
            // todo 전체적으로 쿼리가 굉장히 많이 필요함. 이거 비동기로 ok 반환하고 싶은데 가능한가?
            checkValidation(updateProfileDto);

            // todo 정책 변경으로 Organization 테이블이 필요가 없음;
            //  updateDepartMentTable();
            updateUserTable(userId, updateProfileDto);

            SocialAccountsDelta socialAccountsDelta
                = getSocialAccountsDelta(userId, updateProfileDto.getSocialAccounts());
            updateSocialAccounts(userId, socialAccountsDelta);

            InterestsDelta interestsDelta
                = getInterestDelta(userId, updateProfileDto.getInterests());
            insertNonExistHashtag(interestsDelta.getToAddInterests());
            updateInterests(userId, interestsDelta);

            throw new RuntimeException("test를 위한 exception");
//            return new SuccessResponse();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    //todo 이친구들이 Validatoion을 하는 하나의 클래스가 되는건 어떨까?
    private void checkValidation(Profile updateProfileDto) {
        checkValidInterestFormat(updateProfileDto.getInterests());
    }

    private void checkValidInterestFormat(List<String> interests) {
        if (ObjectUtils.isEmpty(interests)) {
            return;
        }

        boolean isValid = interests.stream()
            .allMatch(
                interest
                    -> StringUtils.startsWith(interest, "#")
            );

        if (!isValid) {
            throw new InvalidUserInputException("모든 해시태그는 #이 붙은 채로 서버로 전송되어야 한다.");
        }
    }

    // Todo 이게 Service단에 있어도 되는 메서드가 맞나..?
    //  그렇다고 이게 DTO에 들어가면 DTO가 Mapper랑 의존관계가 만들어지는데 그건 안될거 같고..
    private SocialAccountsDelta getSocialAccountsDelta(
        long userId,
        List<String> receivedSocialAccounts
    ) {
        List<String> existingSocialAccounts = hyperLinkMapper.findAllByUserId(userId);

        SocialAccountsDelta socialAccountsDelta
            = new SocialAccountsDelta(existingSocialAccounts, receivedSocialAccounts);

        return socialAccountsDelta;
    }

    private InterestsDelta getInterestDelta(long userId, List<String> receivedInterests) {
        List<String> existingInterests = interestMapper.findAllByUserId(userId);

        return new InterestsDelta(existingInterests, receivedInterests);
    }

    private void updateUserTable(long userId, Profile updateProfileDto) {
        if (updateProfileDto.hasProfileFiled()) {
            userMapper.updateProfile(userId, updateProfileDto);
        }
    }

    private void updateSocialAccounts(
        long userId,
        SocialAccountsDelta socialAccountsDelta
    ) {
        if (socialAccountsDelta.hasToAddSocialAccounts()) {
            List<String> toAddSocialAccountUrls = socialAccountsDelta.getToAddSocialAccounts();
            insertSocialAccounts(userId, toAddSocialAccountUrls);
        }

        if (socialAccountsDelta.hasToDeleteSocialAccounts()) {
            List<String> toDeleteSocialAccountUrls = socialAccountsDelta.getToDeleteSocialAccounts();
            deleteSocialAccounts(userId, toDeleteSocialAccountUrls);
        }
    }

    private void insertSocialAccounts(
        long userId,
        List<String> toAddSocialAccountUrls
    ) {
        List<SocialAccounts> toAddSocialAccounts
            = convertSocialAccountUrlToLink(toAddSocialAccountUrls);

        initializeSocialAccounts(toAddSocialAccounts); //메서드 명 수정 필요.

        hyperLinkMapper.insertAll(toAddSocialAccounts, userId);
    }

    private static List<SocialAccounts> convertSocialAccountUrlToLink(
        List<String> socialAccountUrls) {
        return socialAccountUrls.stream()
            .map(SocialAccounts::new)
            .toList();
    }

    private void initializeSocialAccounts(List<SocialAccounts> toAddSocialAccounts) {
        List<Icon> icons = iconMapper.findAll();
        Map<String, Icon> iconMap = makeDomainNameIconMap(icons);

        setIconId(toAddSocialAccounts, iconMap);
    }

    private Map<String, Icon> makeDomainNameIconMap(List<Icon> icons) {
        return icons.stream()
            .collect(Collectors.toMap(
                Icon::getDomain,
                Function.identity()
            ));
    }

    private void setIconId(List<SocialAccounts> toAddSocialAccounts, Map<String, Icon> iconMap) {
        final Long DEFAULT_IMG_ID = 1L; // 미리 지정된 social 주소가 아니면 1로 지정.

        for (SocialAccounts account : toAddSocialAccounts) {
            String domain = account.getDomain();

            if (iconMap.containsKey(domain)) {
                long iconId = iconMap.get(domain).getIconId();
                account.setIconId(iconId);
            } else {
                account.setIconId(DEFAULT_IMG_ID);
            }
        }
    }

    private void deleteSocialAccounts(
        long userId,
        List<String> toDeleteSocialAccountUrls
    ) {
        List<SocialAccounts> toDeleteSocialAccounts = convertSocialAccountUrlToLink(
            toDeleteSocialAccountUrls);
        hyperLinkMapper.deleteAll(toDeleteSocialAccounts, userId);
    }

    private void insertNonExistHashtag(List<String> toAddInterests) {
        List<String> nonExistInterests = extractNonExistHahtagFromToAddInterests(toAddInterests);

        hashtagMapper.insertAll(nonExistInterests);
    }

    private List<String> extractNonExistHahtagFromToAddInterests(List<String> toAddInterests) {
        List<String> existedHashtag = hashtagMapper.retrieveHashtagsIn(toAddInterests);

        return toAddInterests.stream()
            .filter(interest -> !existedHashtag.contains(interest))
            .toList();
    }

    private void updateInterests(long userId, InterestsDelta interestsDelta) {

        if (interestsDelta.hasToAddInterests()) {
            List<String> toAddInterests = interestsDelta.getToAddInterests();
            interestMapper.insertAllIn(userId, toAddInterests);
        }

        if (interestsDelta.hasToDelteInterests()) {
            List<String> toDeleteInterests = interestsDelta.getToDeleteInterests();
            interestMapper.deleteAllIn(userId, toDeleteInterests);
        }
    }

}

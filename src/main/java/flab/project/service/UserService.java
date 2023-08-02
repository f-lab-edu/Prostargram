package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Profile;
import flab.project.data.dto.model.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.mapper.HashtagMapper;
import flab.project.mapper.HyperLinkMapper;
import flab.project.mapper.IconMapper;
import flab.project.mapper.InterestMapper;
import flab.project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void updateUserTable(long userId, Profile updateProfileDto) {
        if (updateProfileDto.hasProfileFiled()) {
            userMapper.updateProfile(userId, updateProfileDto);
        }
    }


}

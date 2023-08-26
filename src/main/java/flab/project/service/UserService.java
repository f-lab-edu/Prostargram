package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.Profile;
import flab.project.data.dto.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;

    public List<User> getFollows(Long userId, GetFollowsType requestType) {
        List<User> result = userMapper.findAll(requestType, userId);

        return result;
    }

    public SuccessResponse<Profile> getProfileInfo(long userId, GetProfileRequestType getProfileRequestType) {
        Profile profileInfo = userMapper.getProfileInfo(userId, getProfileRequestType);

        if (profileInfo == null) {
            throw new NotExistUserException();
        }

        return new SuccessResponse<Profile>(profileInfo);
    }
}

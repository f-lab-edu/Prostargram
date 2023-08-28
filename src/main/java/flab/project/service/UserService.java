package flab.project.service;

import flab.project.data.dto.User;
import flab.project.data.enums.requestparam.GetFollowsType;
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

    public boolean updateProfileImage(long userId, String profileImgUrl) {
        int NumberOfAffectedRow = userMapper.updateProfileImage(userId, profileImgUrl);

        if (NumberOfAffectedRow > 0) {
            return true;
        }

        return false;
    }
}

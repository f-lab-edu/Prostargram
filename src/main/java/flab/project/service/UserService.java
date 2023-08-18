package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.UpdateProfileRequestDto;
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

    public SuccessResponse updateProfile(long userId, UpdateProfileRequestDto updateProfileRequestDto) {
        checkValidation(userId);

        int NumberOfreflectedRow = userMapper.updateProfile(userId, updateProfileRequestDto);

        if (NumberOfreflectedRow == 0) {
            throw new RuntimeException();
        }

        return new SuccessResponse();
    }

    private void checkValidation(long userId) {
        if (userId <= 0) {
            throw new InvalidUserInputException();
        }
    }
}

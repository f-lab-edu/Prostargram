package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    public SuccessResponse updateProfile(long userId, UpdateProfileRequestDto updateProfileRequestDto) {
        checkUserId(userId);

        int numberOfAffectedRow = userMapper.updateProfile(userId, updateProfileRequestDto);

        if (numberOfAffectedRow == 0) {
            throw new RuntimeException();
        }

        return new SuccessResponse();
    }

    private void checkUserId(long userId) {
        if (userId <= 0) {
            throw new InvalidUserInputException();
        }
    }

    public SuccessResponse<Profile> getProfileInfo(long userId, GetProfileRequestType getProfileRequestType) {
        Profile profileInfo = userMapper.getProfileInfo(userId, getProfileRequestType);

        if (profileInfo == null) {
            throw new NotExistUserException();
        }

        return new SuccessResponse<Profile>(profileInfo);
    }
}

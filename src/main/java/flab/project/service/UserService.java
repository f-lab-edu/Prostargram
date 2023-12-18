package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.BasicPostWithUser;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.mapper.PostMapper;
import flab.project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final int MAX_LIMIT = 10;

    private final UserMapper userMapper;
    private final PostMapper postMapper;

    public SuccessResponse updateProfile(long userId, UpdateProfileRequestDto updateProfileRequestDto) {
        checkUserId(userId);

        int numberOfAffectedRow = userMapper.updateProfile(userId, updateProfileRequestDto);

        if (numberOfAffectedRow == 0) {
            throw new RuntimeException();
        }

        return new SuccessResponse();
    }

    public SuccessResponse<Profile> getProfileInfo(long userId, GetProfileRequestType getProfileRequestType) {
        Profile profileInfo = userMapper.getProfileInfo(userId, getProfileRequestType);

        if (profileInfo == null) {
            throw new NotExistUserException();
        }

        return new SuccessResponse<Profile>(profileInfo);
    }

    public boolean updateProfileImage(long userId, String profileImgUrl) {
        int NumberOfAffectedRow = userMapper.updateProfileImage(userId, profileImgUrl);

        return NumberOfAffectedRow == 1;
    }

    // Todo userId 검증은 생략
    public List<BasicPostWithUser> getProfileFeed(long userId, Long lastProfilePostId, long limit) {
            validatePagingData(lastProfilePostId, limit);

            return userMapper.getProfileFeed(userId, lastProfilePostId, limit);
    }

    private void checkUserId(long userId) {
        if (userId <= 0) {
            throw new InvalidUserInputException();
        }
    }

    private void validatePagingData(Long lastProfilePostId, long limit) {

        if (lastProfilePostId != null && lastProfilePostId <= 0) {
            throw new InvalidUserInputException("Invalid lastProfilePostId.");
        }

        if (limit <= 0 || limit > MAX_LIMIT) {
            throw new InvalidUserInputException("Invalid limit.");
        }
    }
}
package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.mapper.UserMapper;
import flab.project.utils.UserRedisUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRedisUtil userRedisUtil;

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

    public List<Profile> getUsersByUserIds(List<Long> userIds) {
        List<Profile> profiles = userRedisUtil.getUsers(userIds);

        List<Long> userIdsNotInRedis = extractUserIdsNotInRedis(profiles, userIds);
        if (!userIdsNotInRedis.isEmpty()) {
            List<Profile> usersInDB = userMapper.findByUserIdIn(userIdsNotInRedis);
            profiles.addAll(usersInDB);
        }

        return profiles;
    }

    private List<Long> extractUserIdsNotInRedis(List<Profile> profiles, List<Long> userIds) {
        List<Long> userIdsNotInRedis = new ArrayList<>();

        for (int index = 0; index < profiles.size(); index++) {
            Profile profile = profiles.get(index);
            if (profile == null) {
                userIdsNotInRedis.add(userIds.get(index));
            }
        }

        return userIdsNotInRedis;
    }

    private void checkUserId(long userId) {
        if (userId <= 0) {
            throw new InvalidUserInputException();
        }
    }
}
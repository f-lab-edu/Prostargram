package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.mapper.UserMapper;
import flab.project.utils.UserRedisUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

    public List<BasicUser> getUsersByUserIds(List<Long> userIds) {
        Set<BasicUser> users = getUsersFromRedis(userIds);

        Set<Long> userIdsNotInRedis = extractUserIdsNotInRedis(new ArrayList<>(users), userIds);
        if (!userIdsNotInRedis.isEmpty()) {
            List<Profile> usersNotInRedis = new ArrayList<>(userMapper.findWhereUserIdIn(userIdsNotInRedis));

            userRedisUtil.saveAll(usersNotInRedis);
            users.addAll(usersNotInRedis);
        }

        return new ArrayList<>(users);
    }

    private Set<BasicUser> getUsersFromRedis(List<Long> userIds) {
        return userRedisUtil.getUsers(userIds)
            .stream()
            .map(BasicUser.class::cast)
            .collect(Collectors.toSet());
    }

    private Set<Long> extractUserIdsNotInRedis(List<BasicUser> users, List<Long> userIds) {
        Set<Long> userIdsNotInRedis = new HashSet<>();

        for (int index = 0; index < users.size(); index++) {
            BasicUser profile = users.get(index);
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
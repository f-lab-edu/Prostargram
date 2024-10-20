package flab.project.domain.user.service;

import flab.project.config.exception.NotExistUserException;
import flab.project.domain.user.model.BasicUser;
import flab.project.domain.user.model.Profile;
import flab.project.domain.user.enums.GetProfileRequestType;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.model.UpdateProfileRequestDto;
import flab.project.domain.user.mapper.UserMapper;
import flab.project.utils.UserRedisUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRedisUtil userRedisUtil;

    public void updateProfile(long userId, UpdateProfileRequestDto updateProfileRequestDto) {
        checkUserId(userId);

        int numberOfAffectedRow = userMapper.updateProfile(userId, updateProfileRequestDto);

        if (numberOfAffectedRow == 0) {
            throw new RuntimeException();
        }
    }

    public Profile getProfileInfo(long userId, GetProfileRequestType getProfileRequestType) {
        Profile profile = userMapper.getProfileInfo(userId, getProfileRequestType);

        if (profile == null) {
            throw new NotExistUserException();
        }

        return profile;
    }

    public boolean updateProfileImage(long userId, String profileImgUrl) {
        int NumberOfAffectedRow = userMapper.updateProfileImage(userId, profileImgUrl);

        return NumberOfAffectedRow == 1;
    }

    public Set<BasicUser> getUsersByUserIds(List<Long> userIds) {
        Set<BasicUser> users = getUsersFromRedis(userIds);

        Set<Long> userIdsNotInRedis = extractUserIdsNotInRedis(new ArrayList<>(users), userIds);
        if (!userIdsNotInRedis.isEmpty()) {
            List<Profile> usersNotInRedis = new ArrayList<>(userMapper.findWhereUserIdIn(userIdsNotInRedis));

            userRedisUtil.saveAll(usersNotInRedis);
            users.addAll(usersNotInRedis);
        }

        return users.stream().filter(Objects::nonNull).collect(Collectors.toSet());
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
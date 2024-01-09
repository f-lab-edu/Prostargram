package flab.project.utils;

import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.Profile;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRedisUtil {

    private final RedisTemplate<Long, Profile> userRedisTemplate;

    // todo: valueOperations.
    public List<Profile> getUsers(List<Long> userIds) {
        ValueOperations<Long, Profile> valueOperations = userRedisTemplate.opsForValue();

        return valueOperations.multiGet(userIds);
    }

    public void save(Profile profile) {
        ValueOperations<Long, Profile> valueOperations = userRedisTemplate.opsForValue();

        valueOperations.set(profile.getUserId(), profile);
    }
}
package flab.project.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowerRedisUtil {

    private final RedisTemplate<Long, Long> followRedisTemplate;

    public boolean exists(Long key) {
        return followRedisTemplate.hasKey(key);
    }

    public void setData(Long key, Long value) {
        ListOperations<Long, Long> listOperations = followRedisTemplate.opsForList();
        listOperations.leftPush(key, value);
    }

    public void delete(Long key) {
        followRedisTemplate.delete(key);
    }
}
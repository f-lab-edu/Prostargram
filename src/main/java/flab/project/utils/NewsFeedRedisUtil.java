package flab.project.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsFeedRedisUtil {

    private final RedisTemplate<Long, Long> newsFeedRedisTemplate;

    // todo: valueOperations.
    public List<Long> getPostIds(Long key) {
        ListOperations<Long, Long> listOperations = newsFeedRedisTemplate.opsForList();
        return listOperations.range(key, 0, 10L);
    }

    public boolean exists(Long key) {
        return newsFeedRedisTemplate.hasKey(key);
    }

    public void delete(Long key) {
        newsFeedRedisTemplate.delete(key);
    }
}
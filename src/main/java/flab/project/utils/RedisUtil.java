package flab.project.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisUtil {

    private final StringRedisTemplate template;

    public String get(String key) {
        ValueOperations<String, String> valueOperations
                = template.opsForValue();
        return valueOperations.get(key);
    }

    public boolean hasKey(String key) {
        return template.hasKey(key);
    }

    public void setWithDuration(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = template.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void delete(String key) {
        template.delete(key);
    }
}
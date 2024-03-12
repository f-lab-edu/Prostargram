package flab.project.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class SignUpRedisUtil {

    private final StringRedisTemplate signUpRedisTemplate;

    public String get(String key) {
        ValueOperations<String, String> valueOperations = signUpRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public boolean exists(String key) {
        return signUpRedisTemplate.hasKey(key);
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = signUpRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void delete(String key) {
        signUpRedisTemplate.delete(key);
    }
}
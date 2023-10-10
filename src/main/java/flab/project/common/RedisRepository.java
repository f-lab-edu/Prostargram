package flab.project.common;

import flab.project.common.jwt.TokenProvider;
import flab.project.config.exception.BlackListTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisRepository {

    private final StringRedisTemplate stringRedisTemplate;
    private final TokenProvider tokenProvider;

    public void saveBlackListToken(String jwt, long userId, Date now) {
        long remainTimeInMillis = getRemainTimeInMillis(jwt, now);

        if (remainTimeInMillis > 0) {
            stringRedisTemplate.opsForValue().set(jwt, String.valueOf(userId), remainTimeInMillis, TimeUnit.MILLISECONDS);
        }
    }

    private long getRemainTimeInMillis(String jwt, Date now) {
        Date accessTokenExpiredAt = tokenProvider.extractExpiredAt(jwt);
        return accessTokenExpiredAt.getTime() - now.getTime();
    }

    public boolean isBlackListToken(String jwt) {
        String retrievedJwt = stringRedisTemplate.opsForValue().get(jwt);

        return retrievedJwt != null;
    }
}
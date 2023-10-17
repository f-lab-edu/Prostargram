package flab.project.cacheManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.data.dto.cache.VoteCache;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VoteCacheManager {

    private final RedisTemplate<String, VoteCache> voteRedisTemplate;
    private final ObjectMapper objectMapper;

    public void addPostVote(long postId, long userId, Set<Long> optionIds) {
        voteRedisTemplate.opsForValue().set(
                "postId: " + postId + ", userId: " + userId,
                new VoteCache(postId, userId, optionIds)
        );
    }

    public VoteCache getVote(long postId, long userId) {
        return voteRedisTemplate.opsForValue()
                .get("postId: " + postId + ", userId: " + userId);
    }
}

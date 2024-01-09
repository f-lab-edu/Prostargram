package flab.project.utils;

import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.BasicPost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostRedisUtil {

    private final RedisTemplate<Long, BasePost> postRedisTemplate;

    // todo: valueOperations.
    public List<BasePost> getFeeds(List<Long> postIds) {
        ValueOperations<Long, BasePost> valueOperations = postRedisTemplate.opsForValue();

        return valueOperations.multiGet(postIds);
    }

    public void save(BasePost post) {
        ValueOperations<Long, BasePost> valueOperations = postRedisTemplate.opsForValue();

        valueOperations.set(post.getPostId(), post);
    }
}
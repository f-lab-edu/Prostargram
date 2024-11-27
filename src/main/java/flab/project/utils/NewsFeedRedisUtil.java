package flab.project.utils;

import flab.project.domain.feed.model.PostIdsAndHasNext;
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
    public PostIdsAndHasNext getPostIds(Long key) {
        ListOperations<Long, Long> listOperations = newsFeedRedisTemplate.opsForList();
        List<Long> postIds = listOperations.range(key, 0, 11L);

        if(postIds.size()<10){
            return new PostIdsAndHasNext(postIds,false);
        }

        postIds.remove(postIds.size() - 1);
        return new PostIdsAndHasNext(postIds, true);
    }

    public boolean exists(Long key) {
        return newsFeedRedisTemplate.hasKey(key);
    }

    public void delete(Long key) {
        newsFeedRedisTemplate.delete(key);
    }
}
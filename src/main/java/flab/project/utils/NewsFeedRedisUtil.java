package flab.project.utils;

import flab.project.domain.feed.model.PostIdsAndHasNext;
import flab.project.domain.feed.service.FeedIdsReader;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsFeedRedisUtil implements FeedIdsReader {

    private final RedisTemplate<Long, Long> newsFeedRedisTemplate;

    // todo: valueOperations.
    @Override
    public PostIdsAndHasNext getPostIds(Long key, Long page) {
        page = Objects.requireNonNullElse(page, 0L);

        ListOperations<Long, Long> listOperations = newsFeedRedisTemplate.opsForList();
        List<Long> postIds = listOperations.range(key, page * PAGE_SIZE, page * (PAGE_SIZE) + (PAGE_SIZE + 1));

        if (postIds.size() < PAGE_SIZE) {
            return new PostIdsAndHasNext(postIds, false);
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
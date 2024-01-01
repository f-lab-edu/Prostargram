package flab.project.common.scheduler;

import flab.project.data.dto.cache.VoteCache;
import flab.project.mapper.VoteMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
public class VoteScheduler {

    private final RedisTemplate<String, VoteCache> voteCacheRedisTemplate;
    private final VoteMapper voteMapper;
    private static final ScanOptions scanOptions = ScanOptions.scanOptions().count(100).build();

    // todo 게시물 수정, 게시물 삭제와 같은 API가 추가 된다면, DB반영에 실패하는 경우가 발생하지 않도록 Redis에서 잘못 된 데이터를 삭제하는 기능이 필요.
//    @Scheduled(fixedDelay = 1000000 * 10)
    public void writeBackVotes() {
        Set<String> redisKeys = scanForKeys();
        if (ObjectUtils.isEmpty(redisKeys)) {
            return;
        }

        List<VoteCache> retrievedVotes = getVotesAndDelete(redisKeys);
        voteMapper.addPostVotes(retrievedVotes);
        // todo 추후 저장에 실패한 votes를 log파일로 저장할 수 있는 메서드가 필요.
    }

    private Set<String> scanForKeys() {
        Set<String> keys = new HashSet<>();

        voteCacheRedisTemplate.execute((RedisCallback<Void>) connection -> {
            try (Cursor<byte[]> cursor = connection.keyCommands().scan(scanOptions)) {
                while (cursor.hasNext()) {
                    keys.add(new String(cursor.next()));
                }
            } catch (Exception e) {
                throw new IllegalStateException();
            }
            return null;
        });
        return keys;
    }

    private List<VoteCache> getVotesAndDelete(Set<String> redisKeys) {
        List<VoteCache> votesFromRedis = new ArrayList<>();
        for (String redisKey : redisKeys) {
            VoteCache vote = voteCacheRedisTemplate.opsForValue().getAndDelete(redisKey);
            votesFromRedis.add(vote);
        }
        return votesFromRedis;
    }
}
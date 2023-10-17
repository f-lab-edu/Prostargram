package flab.project.cacheManager;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import flab.project.data.dto.cache.VoteCache;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class VoteCacheManagerTest {

    @Autowired
    private VoteCacheManager voteCacheManager;

    @DisplayName("redis에 잘 저장되는지를 확인하기위한 임시 test")
    @Test
    void addPostVote() throws JsonProcessingException {
        voteCacheManager.addPostVote(1L, 1L, Set.of(1L, 2L));
        voteCacheManager.addPostVote(1L, 2L, Set.of(2L, 3L));

        //then
        VoteCache retrievedVote = voteCacheManager.getVote(1L,1L);
        VoteCache retrievedVote2 = voteCacheManager.getVote(1L,2L);

        System.out.println("retrievedVote= " + retrievedVote);
        System.out.println("retrievedVote2 = " + retrievedVote2);
    }
}
package flab.project.data.dto.cache;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@RedisHash(value = "vote")
public class VoteCache {

    private long postId;
    private long userId;
    private Set<Long> optionId;
}

package flab.project.domain.post.mapper;

import flab.project.cache.model.VoteCache;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoteMapper {

    void addPostVotes(@Param("votes") List<VoteCache> votes);
}
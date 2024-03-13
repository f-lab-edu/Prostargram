package flab.project.mapper;

import flab.project.data.dto.cache.VoteCache;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface VoteMapper {

    void addPostVotes(@Param("votes") List<VoteCache> votes);
}
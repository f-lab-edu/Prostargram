package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PollPostMapper {

    boolean findAllowMultipleVotes(@Param("postId") long postId);
}

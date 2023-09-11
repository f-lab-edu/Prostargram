package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PollPostMapper {

    boolean checkAllowMultipleVotes(@Param("postId") long postId);
}

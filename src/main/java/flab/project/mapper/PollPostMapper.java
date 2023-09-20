package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

@Mapper
public interface PollPostMapper {

    boolean findAllowMultipleVotes(@Param("postId") long postId);

    LocalDate findStartDate(@Param("postId") long postId);

    LocalDate findEndDate(@Param("postId") long postId);
}

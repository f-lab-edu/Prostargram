package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoteMapper {
    int addPostVote(@Param("postId") long postId, @Param("optionId") long optionId, @Param("userId") long userId);

    List<Long> find(@Param("postId") long postId);

    boolean check(@Param("postId") long postId);
}

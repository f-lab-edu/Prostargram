package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoteMapper {
    void addDebatePostVote(@Param("postId") long postId, @Param("optionId") long optionId, @Param("userId") long userId);

    void addPollPostVote(@Param("postId") long postId, @Param("optionIds") List<Long> optionIds, @Param("userId") long userId);

    List<Long> find(@Param("postId") long postId);

    boolean check(@Param("postId") long postId);
}
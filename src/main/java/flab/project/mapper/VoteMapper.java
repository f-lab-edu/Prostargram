package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface VoteMapper {

    void addPostVote(@Param("postId") long postId, @Param("optionIds") Set<Long> optionIds, @Param("userId") long userId);
}
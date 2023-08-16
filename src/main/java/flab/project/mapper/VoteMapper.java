package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoteMapper {
    int addPostVote(@Param("option_id") long optionId, @Param("user_id") long userId);
}

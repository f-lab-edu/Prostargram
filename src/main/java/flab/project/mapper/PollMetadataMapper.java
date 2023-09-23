package flab.project.mapper;

import flab.project.data.dto.model.PollPeriod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PollMetadataMapper {

    boolean findAllowMultipleVotes(@Param("postId") long postId);

    PollPeriod findPollPeriod(@Param("postId") long postId);
}
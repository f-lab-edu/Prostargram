package flab.project.domain.post.mapper;

import flab.project.domain.post.model.AddPollPostRequest;
import flab.project.domain.post.model.PollPeriod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PollMetadataMapper {

    void save(@Param("pollPost") AddPollPostRequest pollPost);
    
    boolean findAllowMultipleVotes(@Param("postId") long postId);

    PollPeriod findPollPeriod(@Param("postId") long postId);
}
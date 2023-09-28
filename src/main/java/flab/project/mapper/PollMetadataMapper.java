package flab.project.mapper;

import flab.project.data.dto.model.AddPollPostRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PollMetadataMapper {

    void save(@Param("pollPost") AddPollPostRequest pollPost);
}
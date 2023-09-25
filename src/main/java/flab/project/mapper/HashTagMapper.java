package flab.project.mapper;

import flab.project.data.dto.model.HashTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Mapper
public interface HashTagMapper {

    Long getHashtagIdByHashtagName(@Param("hashTagName") String hashTagName);

    Set<HashTag> getHashTagsByHashtagNames(@Param("hashTagNames") Set<String> hashTagNames);

    void save(@Param("hashTag") HashTag hashTagName);

    void saveAll(@Param("hashTags") Set<HashTag> notExistHashTagNames);
}
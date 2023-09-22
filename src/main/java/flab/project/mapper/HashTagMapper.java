package flab.project.mapper;

import flab.project.data.dto.model.HashTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface HashTagMapper {

    Long getHashtagIdByHashtagName(@Param("hashTagName") String hashTagName);

    List<HashTag> getHashTagsByHashtagNames(@Param("hashTagNames") List<String> hashTagNames);

    void save(@Param("hashTag") HashTag hashTagName);

    List<Long> saveAll(@Param("hashTagNames") List<String> notExistHashTagNames);
}
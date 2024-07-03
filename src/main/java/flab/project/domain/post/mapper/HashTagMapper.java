package flab.project.domain.post.mapper;

import flab.project.domain.post.model.HashTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface HashTagMapper {

    Long getHashtagIdByHashtagName(@Param("hashTagName") String hashTagName);

    Set<HashTag> getHashTagsByHashtagNames(@Param("hashTagNames") Set<String> hashTagNames);

    void save(@Param("hashTag") HashTag hashTagName);

    void saveAll(@Param("hashTags") Set<HashTag> notExistHashTagNames);
}
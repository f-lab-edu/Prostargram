package flab.project.mapper;

import flab.project.data.dto.model.HashTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HashTagMapper {

    Long getHashtagIdByHashtagName(@Param("hashTagName") String hashTagName);

    void save(@Param("hashTag") HashTag hashTagName);
}
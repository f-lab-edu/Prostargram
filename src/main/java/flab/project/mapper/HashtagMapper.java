package flab.project.mapper;

import flab.project.data.dto.model.HashTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HashtagMapper {

    Long getHashtagIdByHashtagName(@Param("hashtagName") String hashtagName);

    void save(@Param("hashtag") HashTag hashtagName);
}

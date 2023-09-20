package flab.project.mapper;

import flab.project.data.dto.model.BasicPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {

    int addBasicPost(@Param("basicPost") BasicPost basicPost);
}

package flab.project.mapper;

import flab.project.data.dto.model.AddBasicPostRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {

    int addBasicPost(@Param("userId") long userId, @Param("basicPost") AddBasicPostRequest basicPost);
}

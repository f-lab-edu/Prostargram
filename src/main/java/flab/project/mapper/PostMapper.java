package flab.project.mapper;

import flab.project.data.dto.model.AddPostRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {

    int addPost(@Param("userId") long userId, @Param("post") AddPostRequest post);
}

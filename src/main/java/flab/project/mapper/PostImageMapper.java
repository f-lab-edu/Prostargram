package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface PostImageMapper {

    int saveAll(@Param("postId") long postId, @Param("contentImgUrls") Set<String> contentImgUrls);
}
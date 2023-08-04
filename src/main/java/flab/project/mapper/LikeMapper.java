package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    int addPostLike(@Param("postId") long postId, @Param("userId") long userId);
}

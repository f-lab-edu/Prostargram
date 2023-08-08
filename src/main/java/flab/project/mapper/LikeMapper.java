package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    int addPostLike(@Param("post_id") long postId, @Param("user_id") long userId);
}

package flab.project.domain.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostLikeMapper {

    int addPostLike(@Param("post_id") long postId, @Param("user_id") long userId);

    boolean hasLike(@Param("postId") long postId, @Param("userId") long userId);

    void cancelLike(@Param("postId") long postId, @Param("userId") long userId);
}
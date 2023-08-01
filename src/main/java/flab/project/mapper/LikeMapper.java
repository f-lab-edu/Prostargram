package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    int addPostLike(@Param("postId") long postId, @Param("userId") long userId);

    void deletePostLike(@Param("postId") long postId, @Param("userId") long userId);

    int addCommentLike(@Param("commentId") long commentId, @Param("userId") long userId);

    void deleteCommentLike(@Param("commentId") long commentId, @Param("userId") long userId);
}

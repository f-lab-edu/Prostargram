package flab.project.domain.like.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentLikeMapper {

    void addCommentLike(@Param("commentId") long commentId, @Param("userId") Long userId);

    void cancelCommentLike(@Param("commentId") long commentId, @Param("userId") Long userId);
}

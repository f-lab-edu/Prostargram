package flab.project.domain.comment.mapper;

import flab.project.domain.comment.model.Comment;
import flab.project.domain.comment.model.CommentWithUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    void addComment(@Param("comment") Comment comment);

    List<CommentWithUser> getComments(@Param("postId") long postId, @Param("userId") long userId, @Param("lastCommentId") Long lastCommentId, @Param("limit") long limit);
}
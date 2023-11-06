package flab.project.mapper;

import flab.project.data.dto.CommentWithUser;
import flab.project.data.dto.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    void addComment(@Param("comment") Comment comment);

    List<CommentWithUser> findAllByPostId(long postId, long lastCommentId, long limit);
}
package flab.project.domain.comment.mapper;

import flab.project.domain.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

    void addComment(@Param("comment") Comment comment);
}
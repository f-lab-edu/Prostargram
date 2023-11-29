package flab.project.mapper;

import flab.project.data.dto.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

    void addComment(@Param("comment") Comment comment);
}
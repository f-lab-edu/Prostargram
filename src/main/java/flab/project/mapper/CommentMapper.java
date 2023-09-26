package flab.project.mapper;

import flab.project.data.dto.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

    // Todo comment-mapper.xml에 대댓글 개수를 나타내는 commentCount(이름 변경 예정) 필드 추가 예정
    void addComment(@Param("comment") Comment comment);
}
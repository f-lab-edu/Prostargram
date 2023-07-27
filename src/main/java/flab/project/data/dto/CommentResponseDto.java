package flab.project.data.dto;

import flab.project.data.dto.domain.BasicUser;
import flab.project.data.dto.domain.Comment;

public class CommentResponseDto {
    private final Comment comment;

    private final BasicUser basicUser;

    public CommentResponseDto(Comment comment, BasicUser basicUser) {
        this.comment = comment;
        this.basicUser = basicUser;
    }
}

package flab.project.data.dto;

import flab.project.data.dto.domain.BasicUser;
import flab.project.data.dto.domain.Comment;

public class CommentWithUser {
    private final Comment comment;

    private final BasicUser basicUser;

    public CommentWithUser(Comment comment, BasicUser basicUser) {
        this.comment = comment;
        this.basicUser = basicUser;
    }
}

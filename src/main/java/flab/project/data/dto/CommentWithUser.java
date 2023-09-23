package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Comment;

public class CommentWithUser {
    private final Comment comment;

    private final BasicUser basicUser;

    public CommentWithUser(Comment comment, BasicUser basicUser) {
        this.comment = comment;
        this.basicUser = basicUser;
    }
}
package flab.project.domain.comment.model;

import flab.project.domain.user.model.BasicUser;

public class CommentWithUser {
    private final Comment comment;

    private final BasicUser basicUser;

    public CommentWithUser(Comment comment, BasicUser basicUser) {
        this.comment = comment;
        this.basicUser = basicUser;
    }
}
package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Comment;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommentWithUser {
    private Comment comment;

    private BasicUser basicUser;

    public CommentWithUser(Comment comment, BasicUser basicUser) {
        this.comment = comment;
        this.basicUser = basicUser;
    }
}
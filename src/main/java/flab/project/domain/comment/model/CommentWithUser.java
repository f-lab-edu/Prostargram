package flab.project.domain.comment.model;


import flab.project.domain.user.model.BasicUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentWithUser {

    private Comment comment;

    private BasicUser basicUser;
}
package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentWithUser {
    private Comment comment;

    private BasicUser basicUser;
}
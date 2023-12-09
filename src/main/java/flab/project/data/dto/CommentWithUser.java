package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Comment;
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
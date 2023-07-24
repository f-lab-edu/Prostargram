package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 작성 결과 Dto")
public class CreateCommentResponseDto {

   private final Comment comment;

   private final User user;

    public CreateCommentResponseDto(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }
}
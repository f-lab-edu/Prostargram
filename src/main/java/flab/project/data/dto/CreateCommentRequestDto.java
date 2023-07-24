package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 작성 Dto")
public class CreateCommentRequestDto {

    private final Comment comment;

    public CreateCommentRequestDto(Comment comment) {
        this.comment = comment;
    }
}

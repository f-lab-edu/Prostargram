package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 작성 결과를 나타내는 Dto")
public class CreateCommentResponseDto {

	@Schema(example = "1")
	private final long commentId;

	public CreateCommentResponseDto(long commentId) {
		this.commentId = commentId;
	}
}
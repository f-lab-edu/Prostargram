package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "피드 작성 결과를 나타내는 DTO")
public class CreatePostResponseDto {

	// final을 적용하는 건 어떨까?
	@Schema(example = "1")
	private final long postId;

	public CreatePostResponseDto(long postId) {
		this.postId = postId;
	}
}

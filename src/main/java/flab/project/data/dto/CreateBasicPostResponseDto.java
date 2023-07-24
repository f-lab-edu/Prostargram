package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "일반 피드 작성 결과 Dto")
public class CreateBasicPostResponseDto {

	private final BasicPost basicPost;

	private final BasicUser basicUser;

	public CreateBasicPostResponseDto(BasicPost basicPost, BasicUser basicUser) {
		this.basicPost = basicPost;
		this.basicUser = basicUser;
	}
}

package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "일반 피드 작성 Dto")
public class CreateBasicPostRequestDto {

    private final BasicPost basicPost;

    public CreateBasicPostRequestDto(BasicPost basicPost) {
        this.basicPost = basicPost;
    }
}


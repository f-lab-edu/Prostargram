package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "일반 피드 작성 Dto")
public class CreateBasicPostRequestDto extends BasicPost {

    @Schema(example = "https://imageUrl.url", nullable = false)
    private final List<String> contentImgUrls;

    public CreateBasicPostRequestDto(List<String> contentImgUrls) {
        this.contentImgUrls = contentImgUrls;
    }
}


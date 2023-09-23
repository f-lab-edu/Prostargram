package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Schema(description = "일반 게시물 Dto")
public class BasicPost extends BasePost {

    @Schema(example = "https://imageUrl.url", nullable = false)
    private List<String> contentImgUrls;
}
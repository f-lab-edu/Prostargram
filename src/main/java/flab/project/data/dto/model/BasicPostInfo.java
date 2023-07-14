package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드의 기본 정보")
public abstract class BasicPostInfo {

    @Schema(example = "1")
    private long userId;

    @Schema(example = "이은비")
    private String userName;

    @Schema(example = "https://profileImg.url")
    private String profileImgUrl;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    private String content;

    @Schema(example = "https://imageUrl.url", nullable = false)
    private List<String> contentImgUrls;

    @Schema(example = "java")
    private List<String> hashTags;

}

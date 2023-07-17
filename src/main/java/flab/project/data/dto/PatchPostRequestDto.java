package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드 수정 Dto")
public class PatchPostRequestDto {

    @Schema(example = "1")
    private final long postId;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    private final String content;

    @Schema(example = "java")
    private final List<String> hashTags;

    public PatchPostRequestDto(long postId, String content, List<String> hashTags) {
        this.postId = postId;
        this.content = content;
        this.hashTags = hashTags;
    }
}

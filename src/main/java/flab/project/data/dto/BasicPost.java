package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "일반 피드 Dto")
public class BasicPost {

    @Schema(example = "1")
    private long userId;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    private String content;

    @Schema(example = "java")
    private List<String> hashTags;

    @Schema(example = "일반")
    private String type;

}

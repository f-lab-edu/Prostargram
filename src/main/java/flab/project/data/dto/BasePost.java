package flab.project.data.dto;

import flab.project.data.enums.LikeType;
import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "기본 피드 Dto")
public class BasePost {

    @Schema(example = "1")
    private long userId;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    private String content;

    @Schema(example = "좋아요 또는 좋아요 취소 enum")
    private LikeType likeType;

    @Schema(example = "#java")
    private List<String> hashTags;

    @Schema(description = "Post 종류 enum")
    private PostType postType;

}

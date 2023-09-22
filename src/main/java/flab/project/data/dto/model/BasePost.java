package flab.project.data.dto.model;

import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Schema(description = "기본 게시물 Dto")
public abstract class BasePost {

    @Schema(example = "1")
    protected long postId;

    @Schema(example = "1")
    protected long userId;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    protected String content;

    @Schema(example = "#java")
    protected List<String> hashTags;

    @Schema(description = "Post 종류 enum")
    protected PostType postType;

    @Schema(example = "1400", defaultValue = "0")
    protected long likeCount;

    @Schema(example = "14", defaultValue = "0")
    protected long commentCount;

    @Schema(example = "방금 전")
    protected String createAt;
}

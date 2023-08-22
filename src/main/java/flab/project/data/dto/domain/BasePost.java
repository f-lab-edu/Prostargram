package flab.project.data.dto.domain;

import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Schema(description = "기본 게시물 Dto")
public abstract class BasePost {

    @Schema(example = "1")
    protected final long userId;

    @Schema(example = "1")
    protected final long postId;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    protected final String content;

    @Schema(example = "#java")
    protected final List<String> hashTags;

    @Schema(description = "Post 종류 enum")
    protected final PostType postType;

    @Schema(example = "1400", defaultValue = "0")
    protected final long likeCount;

    @Schema(example = "14", defaultValue = "0")
    protected final long commentCount;

    @Schema(example = "방금 전")
    protected final String createTime;
}

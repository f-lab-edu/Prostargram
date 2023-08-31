package flab.project.data.dto.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "댓글 Dto")
public class Comment {

    @Schema(example = "1")
    protected long postId;

    @Schema(example = "1")
    protected long userId;

    @Schema(example = "1")
    protected long commentId;

    @Schema(example = "1", description = "대댓글이 존재할 경우, 가장 최상단의 댓글")
    protected long rootId;

    @Schema(example = "화이팅 합시다!")
    protected String content;

    @Schema(example = "방금 전")
    protected String createTime;

    @Schema(example = "1400", defaultValue = "0")
    protected long likeCount;

    @Schema(example = "14", defaultValue = "0")
    protected long commentCount;
}

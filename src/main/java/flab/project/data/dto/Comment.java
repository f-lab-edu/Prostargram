package flab.project.data.dto;

import flab.project.data.enums.LikeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 Dto")
public class Comment {

    @Schema(example = "1")
    private long postId;

    @Schema(example = "1")
    private long userId;

    @Schema(example = "화이팅 합시다!")
    private String content;

    @Schema(example = "좋아요 또는 좋아요 취소 enum")
    private LikeType likeType;

    @Schema(example = "1", description = "대댓글이 존재할 경우, 가장 최상단의 댓글")
    private long rootId;

}

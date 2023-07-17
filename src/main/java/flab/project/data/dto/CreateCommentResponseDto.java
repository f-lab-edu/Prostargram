package flab.project.data.dto;

import flab.project.data.dto.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 작성 결과 Dto")
public class CreateCommentResponseDto extends Comment {

    @Schema(example = "1")
    private final long commentId;

    @Schema(example = "정민욱")
    private final String userName;

    @Schema(example = "https://profileImg.url")
    private final String profileImgUrl;

    @Schema(example = "방금 전")
    private final String createTime;

    @Schema(example = "1.4k", defaultValue = "0")
    private final long likeCount;

    @Schema(example = "14", defaultValue = "0")
    private final long commentCount;

    public CreateCommentResponseDto(String userName, String profileImgUrl, String createTime, long commentId, long likeCount, long commentCount) {
        this.commentId = commentId;
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.createTime = createTime;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
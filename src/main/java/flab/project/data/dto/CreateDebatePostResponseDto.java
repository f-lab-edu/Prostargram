package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "토론 피드 작성 결과 Dto")
public class CreateDebatePostResponseDto extends DebatePost {

    @Schema(example = "1")
    private final long postId;

    @Schema(example = "이은비")
    private final String userName;

    @Schema(example = "https://profileImg.url")
    private final String profileImgUrl;

    @Schema(example = "1.4k", defaultValue = "0")
    private final long likeCount;

    @Schema(example = "14", defaultValue = "0")
    private final long commentCount;

    @Schema(example = "방금 전")
    private final String creatTime;

    public CreateDebatePostResponseDto(long postId, String userName, String profileImgUrl, long likeCount, long commentCount, String creatTime) {
        this.postId = postId;
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.creatTime = creatTime;
    }
}

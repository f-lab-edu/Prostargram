package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "통계 피드 상세 보기")
public class GetDetailPollPostResponseDto {

    private final PollPost pollPost;

    private final User user;

    @Schema(description = "좋아요 순 베스트 댓글", example = "[1,2,3]")
    private final List<Comment> bestComments;

    @Schema(description = "최신 순 댓글", example = "[1,2,3,4,5]")
    private final List<Comment> recentComments;

    public GetDetailPollPostResponseDto(PollPost pollPost, User user, List<Comment> bestComments, List<Comment> recentComments) {
        this.pollPost = pollPost;
        this.user = user;
        this.bestComments = bestComments;
        this.recentComments = recentComments;
    }
}

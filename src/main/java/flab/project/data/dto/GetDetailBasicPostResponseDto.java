package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "일반 피드 상세 보기 Dto")
public class GetDetailBasicPostResponseDto {

    private final BasicPost basicPost;

    private final User user;

    @Schema(description = "좋아요 순 베스트 댓글", example = "[1,2,3]")
    private final List<Comment> bestComments;

    @Schema(description = "최신 순 댓글", example = "[1,2,3,4,5]")
    private final List<Comment> recentComments;

    public GetDetailBasicPostResponseDto(BasicPost basicPost, User user, List<Comment> bestComments, List<Comment> recentComments) {
        this.basicPost = basicPost;
        this.user = user;
        this.bestComments = bestComments;
        this.recentComments = recentComments;
    }
}

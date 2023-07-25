package flab.project.data.dto;

import flab.project.data.dto.domain.BasicPost;
import flab.project.data.dto.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "일반 피드 상세 보기 Dto")
public class GetDetailBasicPostResponseDto {

    private final BasicPost basicPost;

    private final User user;

    @Schema(description = "좋아요 순 베스트 댓글 3개 + 일반 댓글들(ex. 최신 순으로 2개)", example = "[1, 2, 3, 4, 5]")
    private final List<Comment> comments;

    public GetDetailBasicPostResponseDto(BasicPost basicPost, User user, List<Comment> comments) {
        this.basicPost = basicPost;
        this.user = user;
        this.comments = comments;
    }
}

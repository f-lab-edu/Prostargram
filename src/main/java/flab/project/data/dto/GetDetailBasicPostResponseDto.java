package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "일반 피드 상세 보기 Dto")
public class GetDetailBasicPostResponseDto extends BasicPost {

    @Schema(example = "1")
    private final long postId;

    @Schema(example = "이은비")
    private final String userName;

    @Schema(example = "https://profileImg.url")
    private final String profileImgUrl;

    @Schema(example = "https://imageUrl.url", nullable = false)
    private final List<String> contentImgUrls;

    @Schema(example = "1.4k")
    private final long likeCounts;

    @Schema(example = "14")
    private final long commentCounts;

    @Schema(example = "방금 전")
    private final String createTime;

    @Schema(description = "좋아요 순 베스트 댓글", example = "[1,2,3]")
    private final List<Comment> bestComments;

    @Schema(description = "최신 순 댓글", example = "[1,2,3,4,5]")
    private final List<Comment> recentComments;

    public GetDetailBasicPostResponseDto(String userName, String profileImgUrl, List<String> contentImgUrls, long postId, long likeCounts, long commentCounts, String createTime, List<Comment> bestComments, List<Comment> recentComments) {
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.contentImgUrls = contentImgUrls;
        this.postId = postId;
        this.likeCounts = likeCounts;
        this.commentCounts = commentCounts;
        this.createTime = createTime;
        this.bestComments = bestComments;
        this.recentComments = recentComments;
    }
}

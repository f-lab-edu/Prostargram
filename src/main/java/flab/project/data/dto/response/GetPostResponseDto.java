package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드 상세 보기를 나타내는 Dto")
public class GetPostResponseDto {

    // Todo 겹치는 정보가 많은데, 잘 활용할 수 없을까?
    @Schema(example = "1")
    private final long postId;

    @Schema(example = "1")
    private final long userId;

    @Schema(example = "이은비")
    private final String userName;

    @Schema(example = "https://profileImg.url")
    private final String profileImgUrl;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    private final String content;

    @Schema(example = "https://imageUrl.url", nullable = false)
    private final List<String> contentImgUrls;

    @Schema(example = "방금 전")
    private final String creadtedAt;

    @Schema(example = "java")
    private final List<String> hashTags;

    @Schema(example = "1.4k")
    private final long likeCounts;

    @Schema(example = "14")
    private final long commentCounts;

    // Todo 베스트 댓글 구현

    public GetPostResponseDto(long userId, String userName, String profileImgUrl, String content, List<String> contentImgUrls, String creadtedAt, List<String> hashTags, long postId, long likeCounts, long commentCounts) {
        this.userId = userId;
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.content = content;
        this.contentImgUrls = contentImgUrls;
        this.creadtedAt = creadtedAt;
        this.hashTags = hashTags;
        this.postId = postId;
        this.likeCounts = likeCounts;
        this.commentCounts = commentCounts;
    }
}

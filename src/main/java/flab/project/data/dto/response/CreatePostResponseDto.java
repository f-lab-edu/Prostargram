package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드 작성 결과를 나타내는 DTO")
public class CreatePostResponseDto {

	@Schema(example = "1")
	private final long postId;

	// Long Polling 방식을 사용하므로 request의 데이터 = response의 데이터
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

	@Schema(example = "1.4k", defaultValue = "0")
	private final long likeCounts;

	// Todo ERD 상에서 댓글 개수는 어떤 필드로 표현되고 있는거지?
	@Schema(example = "14", defaultValue = "0")
	private final long commentCounts;

	@Schema(example = "방금 전")
	private final String creadtedAt;

	@Schema(example = "java")
	private final List<String> hashTags;

	public CreatePostResponseDto(long userId, String userName, String profileImgUrl, String content, List<String> contentImgUrls, String creadtedAt, List<String> hashTags, long postId, long likeCounts, long commentCounts) {
		this.postId = postId;
		this.userId = userId;
		this.userName = userName;
		this.profileImgUrl = profileImgUrl;
		this.content = content;
		this.contentImgUrls = contentImgUrls;
		this.creadtedAt = creadtedAt;
		this.likeCounts = likeCounts;
		this.commentCounts = commentCounts;
		this.hashTags = hashTags;
	}
}

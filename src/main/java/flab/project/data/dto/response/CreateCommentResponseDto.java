package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 작성 결과를 나타내는 Dto")
public class CreateCommentResponseDto {

	// Todo BasicUserInfo 업데이트 시 반영 예정
	@Schema(example = "1")
	private final String postId;

	@Schema(example = "1")
	private final long commentId;

	@Schema(example = "1")
	private final String userId;

	@Schema(example = "정민욱")
	private final String userName;

	@Schema(example = "https://profileImg.url")
	private final String profileImgUrl;

	@Schema(example = "방금 전")
	private final String createdAt;

	@Schema(example = "요즘도 열심히 알고리즘 풀이를 진행하시네요.", maxLength = 100)
	private final String content;

	@Schema(example = "1.4k", defaultValue = "0")
	private final long likeCounts;

	@Schema(example = "14", defaultValue = "0")
	private final long commentCounts;

	public CreateCommentResponseDto(String postId, String userId, String userName, String profileImgUrl, String createdAt, String content, long commentId, long likeCounts, long commentCounts) {
		this.postId = postId;
		this.commentId = commentId;
		this.userId = userId;
		this.userName = userName;
		this.profileImgUrl = profileImgUrl;
		this.createdAt = createdAt;
		this.content = content;
		this.likeCounts = likeCounts;
		this.commentCounts = commentCounts;
	}
}
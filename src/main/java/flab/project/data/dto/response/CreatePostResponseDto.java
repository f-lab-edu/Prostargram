package flab.project.data.dto.response;

import flab.project.data.dto.model.BasicPostInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드 작성 결과를 나타내는 DTO")
public class CreatePostResponseDto extends BasicPostInfo {

	@Schema(example = "1")
	private final long postId;

	@Schema(example = "https://imageUrl.url", nullable = false)
	private final List<String> contentImgUrls;

	@Schema(example = "1.4k", defaultValue = "0")
	private final long likeCounts;

	// Todo ERD 상에서 댓글 개수는 어떤 필드로 표현되고 있는거지?
	@Schema(example = "14", defaultValue = "0")
	private final long commentCounts;

	@Schema(example = "방금 전")
	private final String creadtedAt;

	public CreatePostResponseDto(List<String> contentImgUrls, String creadtedAt, long postId, long likeCounts, long commentCounts) {
		this.postId = postId;
		this.contentImgUrls = contentImgUrls;
		this.creadtedAt = creadtedAt;
		this.likeCounts = likeCounts;
		this.commentCounts = commentCounts;
	}
}

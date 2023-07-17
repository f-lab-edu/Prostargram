package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "일반 피드 작성 결과 Dto")
public class CreateBasicPostResponseDto extends BasicPost {

	@Schema(example = "1")
	private final long postId;

	@Schema(example = "이은비")
	private final String userName;

	@Schema(example = "https://profileImg.url")
	private final String profileImgUrl;

	@Schema(example = "https://imageUrl.url", nullable = false)
	private final List<String> contentImgUrls;

	@Schema(example = "1.4k", defaultValue = "0")
	private final long likeCount;

	@Schema(example = "14", defaultValue = "0")
	private final long commentCount;

	@Schema(example = "방금 전")
	private final String creatTime;

	public CreateBasicPostResponseDto(long postId, String userName, String profileImgUrl, List<String> contentImgUrls, long likeCount, long commentCount, String creatTime) {
		this.postId = postId;
		this.userName = userName;
		this.profileImgUrl = profileImgUrl;
		this.contentImgUrls = contentImgUrls;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.creatTime = creatTime;
	}
}

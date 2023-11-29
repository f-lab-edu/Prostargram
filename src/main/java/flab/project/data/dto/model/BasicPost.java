package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
@Schema(description = "일반 게시물 Dto")
public class BasicPost extends BasePost {

    @Schema(example = "https://imageUrl.url", nullable = false)
    private Set<String> contentImgUrls;

    public BasicPost(AddBasicPostRequest addBasicPostRequest, long userId, Set<String> contentImgUrls) {
        this.postId = addBasicPostRequest.getPostId();
        this.userId = userId;
        this.content = addBasicPostRequest.getContent();
        this.hashTagNames = addBasicPostRequest.getHashTagNames();
        this.postType = addBasicPostRequest.getPostType();
        this.createdAt = addBasicPostRequest.getCreatedAt();
        this.contentImgUrls = contentImgUrls;
        this.likeCount = 0;
        this.commentCount = 0;
        this.isLike = false;
        this.isFollow = false;
    }
}
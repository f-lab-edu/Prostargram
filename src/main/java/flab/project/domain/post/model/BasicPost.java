package flab.project.domain.post.model;

import flab.project.domain.post.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import lombok.ToString;

@ToString
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

    @Builder
    public BasicPost(long postId, long userId, String content, Set<String> hashTagNames,
        PostType postType, long likeCount, long commentCount, Timestamp createdAt,
        Boolean isLike, Boolean isFollow, Set<String> contentImgUrls) {
        super(postId, userId, content, hashTagNames, postType, likeCount, commentCount, createdAt, isLike, isFollow);
        this.contentImgUrls = contentImgUrls;
    }
}
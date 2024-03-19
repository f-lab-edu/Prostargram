package flab.project.data.dto.model;

import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Schema(description = "토론 게시물 Dto")
public class DebatePost extends BasePost {

    private Set<Option> options;

    private Integer selectedOptionId;

    public DebatePost(AddDebatePostRequest debatePostRequest, long userId, Set<Option> options) {
        this.postId = debatePostRequest.getPostId();
        this.userId = userId;
        this.content = debatePostRequest.getContent();
        this.hashTagNames = debatePostRequest.getHashTagNames();
        this.postType = debatePostRequest.getPostType();
        this.createdAt = debatePostRequest.getCreatedAt();
        this.options = options;
        this.selectedOptionId = null;
        this.likeCount = 0;
        this.commentCount = 0;
        this.isLike = false;
        this.isFollow = false;
    }

    @Builder
    public DebatePost(long postId, long userId, String content, Set<String> hashTagNames, PostType postType,
        long likeCount,
        long commentCount, Timestamp createdAt, Boolean isLike, Boolean isFollow, Set<Option> options,
        Integer selectedOptionId) {
        super(postId, userId, content, hashTagNames, postType, likeCount, commentCount, createdAt, isLike, isFollow);
        this.options = options;
        this.selectedOptionId = selectedOptionId;
    }
}
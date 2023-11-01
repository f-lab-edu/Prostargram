package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.Set;

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
}
package flab.project.domain.post.model;

import flab.project.domain.user.model.Option;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Schema(description = "통계 게시물 Dto")
public class PollPost extends BasePost {

    private Set<Option> options;

    @Schema(example = "2023-07-01")
    private LocalDate startDate;

    @Schema(example = "2023-07-14")
    private LocalDate endDate;

    private List<Integer> selectedOptionIds;

    public PollPost(AddPollPostRequest pollPostRequest, long userId, Set<Option> options) {
        this.postId = pollPostRequest.getPostId();
        this.userId = userId;
        this.content = pollPostRequest.getContent();
        this.hashTagNames = pollPostRequest.getHashTagNames();
        this.postType = pollPostRequest.getPostType();
        this.createdAt = pollPostRequest.getCreatedAt();
        this.options = options;
        this.startDate = pollPostRequest.getStartDate();
        this.endDate = pollPostRequest.getEndDate();
        this.selectedOptionIds = null;
        this.likeCount = 0;
        this.commentCount = 0;
        this.isLike = false;
        this.isFollow = false;
    }
}
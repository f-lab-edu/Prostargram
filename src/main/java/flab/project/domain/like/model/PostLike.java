package flab.project.domain.like.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLike {
    private long postId;
    private long likeCount;

    public PostLike(long postId, long likeCount) {
        this.postId = postId;
        this.likeCount = likeCount;
    }
}

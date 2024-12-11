package flab.project.domain.post.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCount {

    private long postId;
    private long commentCount;

    public CommentCount(long postId, long commentCount) {
        this.postId = postId;
        this.commentCount = commentCount;
    }
}

package flab.project.data.dto;

import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "토론 피드 Dto")
public class DebatePost extends BasePost {

    @Schema(example = "1")
    private final long optionId;

    public DebatePost(long userId, long postId, String content, List<String> hashTags, PostType postType, long likeCount, long commentCount, String createTime, long optionId) {
        super(userId, postId, content, hashTags, postType, likeCount, commentCount, createTime);
        this.optionId = optionId;
    }
}

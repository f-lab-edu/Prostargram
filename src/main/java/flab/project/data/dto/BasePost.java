package flab.project.data.dto;

import flab.project.data.enums.LikeType;
import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "기본 피드 Dto")
public class BasePost {

    @Schema(example = "1")
    protected final long userId;

    @Schema(example = "1")
    protected final long postId;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    protected final String content;

    @Schema(example = "#java")
    protected final List<String> hashTags;

    @Schema(description = "Post 종류 enum")
    protected final PostType postType;

    @Schema(example = "1400", defaultValue = "0")
    protected final long likeCount;

    @Schema(example = "14", defaultValue = "0")
    protected final long commentCount;

    @Schema(example = "방금 전")
    protected final String createTime;

    public BasePost(long userId, long postId, String content, List<String> hashTags, PostType postType, long likeCount, long commentCount, String createTime) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        this.hashTags = hashTags;
        this.postType = postType;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createTime = createTime;
    }
}

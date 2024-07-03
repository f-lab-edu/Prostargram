package flab.project.domain.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import flab.project.domain.post.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "postType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BasicPost.class, name = "BASIC"),
    @JsonSubTypes.Type(value = DebatePost.class, name = "DEBATE"),
    @JsonSubTypes.Type(value = PollPost.class, name = "POLL")
})
@RedisHash(value = "post")
@NoArgsConstructor
@Getter
@Schema(description = "기본 게시물 Dto")
public abstract class BasePost {

    @Id
    @Schema(example = "1")
    protected long postId;

    @Schema(example = "1")
    protected long userId;

    @Schema(example = "오늘 깃허브에 알고리즘 관련 내용을 정리했습니다.", maxLength = 100)
    protected String content;

    @Schema(example = "#java")
    protected Set<String> hashTagNames;

    @Schema(description = "Post 종류 enum")
    protected PostType postType;

    @Schema(example = "1400", defaultValue = "0")
    protected long likeCount;

    @Schema(example = "14", defaultValue = "0")
    protected long commentCount;

    @Schema(example = "2023-09-19 12:00:00")
    protected Timestamp createdAt;

    @JsonProperty("isLike")
    @Schema(example = "false")
    protected Boolean isLike;

    @JsonProperty("isFollow")
    @Schema(example = "false")
    protected Boolean isFollow;

    public BasePost(long postId, long userId, String content, Set<String> hashTagNames, PostType postType,
        long likeCount,
        long commentCount, Timestamp createdAt, Boolean isLike, Boolean isFollow) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.hashTagNames = hashTagNames;
        this.postType = postType;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.isLike = isLike;
        this.isFollow = isFollow;
    }
}
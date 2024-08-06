package flab.project.domain.comment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 Dto")
public class Comment {

    @Schema(example = "1")
    private long commentId;

    @Schema(example = "1")
    private long postId;

    @Schema(example = "1")
    private long userId;

    @Schema(description = "대댓글이 존재할 경우, 가장 최상단의 댓글", example = "1", nullable = true)
    @Setter
    private Long parentId;

    @JsonProperty("isLike")
    @Schema(example = "false")
    private Boolean isLike;

    @Schema(example = "화이팅 합시다!")
    private String content;

    @Schema(example = "2023-09-19 12:00:00")
    private LocalDateTime createdAt;

    @Schema(example = "1400", defaultValue = "0")
    private long likeCount;

    @Schema(description = "대댓글의 개수", example = "14", defaultValue = "0")
    private long childrenCount;

    @Builder
    public Comment(long postId, long userId, Long parentId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.parentId = parentId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
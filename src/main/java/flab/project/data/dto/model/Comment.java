package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@Schema(description = "댓글 Dto")
public class Comment {

    @Schema(example = "1")
    private long postId;

    @Schema(example = "1")
    private long userId;

    @Schema(example = "1")
    private long commentId;

    @Schema(example = "1", description = "대댓글이 존재할 경우, 가장 최상단의 댓글", nullable = true)
    @Setter
    private Long rootId;

    @Schema(example = "화이팅 합시다!")
    private String content;

    @Schema(example = "2023-09-19 12:00:00")
    private LocalDateTime createdAt;

    @Schema(example = "1400", defaultValue = "0")
    private long likeCount;

    @Schema(example = "14", defaultValue = "0")
    private long commentCount;
}
package flab.project.data.dto.domain;

import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Schema(description = "통계 게시물 Dto")
public class PollPost extends BasePost {

    private final List<Option> options;

    @Schema(example = "2023-07-01")
    private final LocalDate startDate;

    @Schema(example = "2023-07-14")
    private final LocalDate endDate;

    public PollPost(long userId, long postId, String content, List<String> hashTags, PostType postType, long likeCount, long commentCount, String createTime, List<Option> options, LocalDate startDate, LocalDate endDate) {
        super(userId, postId, content, hashTags, postType, likeCount, commentCount, createTime);
        this.options = options;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

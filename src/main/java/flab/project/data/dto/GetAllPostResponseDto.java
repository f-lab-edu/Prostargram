package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드 목록 보기 Dto")
public class GetAllPostResponseDto {

    @Schema(example = "[피드 1, 피드2, 피드3, 피드4, 피드5]")
    private final List<BasicPost> basicPosts;

    @Schema(example = "[피드 1, 피드2, 피드3, 피드4, 피드5]")
    private final List<DebatePost> debatePosts;

    @Schema(example = "[피드 1, 피드2, 피드3, 피드4, 피드5]")
    private final List<PollPost> pollPosts;

    public GetAllPostResponseDto(List<BasicPost> basicPosts, List<DebatePost> debatePosts, List<PollPost> pollPosts) {
        this.basicPosts = basicPosts;
        this.debatePosts = debatePosts;
        this.pollPosts = pollPosts;
    }
}

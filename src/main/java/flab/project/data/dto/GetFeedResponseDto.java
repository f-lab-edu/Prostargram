package flab.project.data.dto;

import flab.project.data.dto.domain.BasePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드 목록 보기 Dto")
public class GetFeedResponseDto {

    @Schema(example = "[피드 1, 피드2, 피드3, 피드4, 피드5]")
    private final List<BasePost> posts;

    public GetFeedResponseDto(List<BasePost> posts) {
        this.posts = posts;
    }
}

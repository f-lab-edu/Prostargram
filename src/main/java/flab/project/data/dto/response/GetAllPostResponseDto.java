package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "피드 목록을 가져오는 Response Dto")
public class GetAllPostResponseDto {

    @Schema(example = "[피드 1, 피드2, 피드3, 피드4, 피드5]")
    private final List<GetPostResponseDto> posts;

    public GetAllPostResponseDto(List<GetPostResponseDto> posts) {
        this.posts = posts;
    }
}

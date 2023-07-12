// ListPostsResponseDto
package flab.project.data.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.List;

@Getter
@Schema(description = "피드 목록을 가져오는 Response Dto")
public class GetAllPostResponseDto {

    @Schema(example = "[1, 2, 3, 4, 5]")
    private List<Long> postIds;

    @Schema(example = "['피드1 내용', '피드2 내용', '피드3 내용', '피드4 내용', '피드5 내용']")
    private List<String> contents;

    public GetAllPostResponseDto(List<Long> postIds, List<String> contents) {
        this.postIds = postIds;
        this.contents = contents;
    }
}

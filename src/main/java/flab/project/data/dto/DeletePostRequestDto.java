package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "피드 삭제 Dto")
public class DeletePostRequestDto{

    @Schema(example = "1")
    private final long postId;

    public DeletePostRequestDto(long postId) {
        this.postId = postId;
    }
}

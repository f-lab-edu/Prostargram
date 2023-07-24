package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "토론 피드 작성 Dto")
public class CreateDebatePostRequestDto {

    private final DebatePost debatePost;

    public CreateDebatePostRequestDto(DebatePost debatePost) {
        this.debatePost = debatePost;
    }
}

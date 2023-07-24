package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "토론 피드 작성 결과 Dto")
public class CreateDebatePostResponseDto {

    private final DebatePost debatePost;

    private final BasicUser basicUser;

    public CreateDebatePostResponseDto(DebatePost debatePost, BasicUser basicUser) {
        this.debatePost = debatePost;
        this.basicUser = basicUser;
    }
}

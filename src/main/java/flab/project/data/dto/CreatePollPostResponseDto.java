package flab.project.data.dto;

import flab.project.data.dto.model.BasicUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "통계 피드 작성 결과 Dto")
public class CreatePollPostResponseDto{

   private final PollPost pollPost;

   private final BasicUser basicUser;

    public CreatePollPostResponseDto(PollPost pollPost, BasicUser basicUser) {
        this.pollPost = pollPost;
        this.basicUser = basicUser;
    }
}

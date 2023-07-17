package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "통계 피드 작성 Dto")
public class CreatePollPostRequestDto extends PollPost {

}

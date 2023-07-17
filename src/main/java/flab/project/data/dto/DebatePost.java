package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "토론 피드 Dto")
public class DebatePost extends BasicPost {

    @Schema(example = "1")
    private long optionId;

}

package flab.project.data.dto.request;

import flab.project.data.enums.requestparam.UpdateLikeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "좋아요 또는 좋아요 취소를 하는 Dto")
public class UpdatePostLikeRequestDto {

    @Schema(example = "1")
    private long postId;

    // Todo QueryString vs Body
    private UpdateLikeType updateLikeType;

    @Schema(example = "1")
    private long userId;
}

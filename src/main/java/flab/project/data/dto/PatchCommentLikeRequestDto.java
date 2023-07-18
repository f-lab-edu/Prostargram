package flab.project.data.dto;

import flab.project.data.enums.requestparam.UpdateLikeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 좋아요 또는 좋아요 취소 Dto")
public class PatchCommentLikeRequestDto {

    @Schema(example = "UPDATE_LIKE_REQUEST")
    private final UpdateLikeType updateLikeType;

    public PatchCommentLikeRequestDto(UpdateLikeType updateLikeType) {
        this.updateLikeType = updateLikeType;
    }
}

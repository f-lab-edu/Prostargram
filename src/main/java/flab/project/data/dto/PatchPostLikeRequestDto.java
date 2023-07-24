package flab.project.data.dto;

import flab.project.data.enums.LikeType;
import flab.project.data.enums.requestparam.UpdateLikeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "피드 좋아요 또는 좋아요 취소 Dto")
public class PatchPostLikeRequestDto {

    @Schema(example = "PATCH_LIKE_REQUEST")
    private final LikeType likeType;

    public PatchPostLikeRequestDto(LikeType likeType) {
        this.likeType = likeType;
    }
}

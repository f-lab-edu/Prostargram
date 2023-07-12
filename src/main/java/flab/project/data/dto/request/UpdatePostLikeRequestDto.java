package flab.project.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "좋아요 또는 좋아요 취소를 하는 Dto")
public class UpdatePostLikeRequestDto {

    @Schema(example = "1")
    private long postId;

    // enum - QueryString vs Body
    @Schema(description = "좋아요 또는 좋아요 취소")
    // 변수 이름 수정 예정
    private boolean isLiked;

    @Schema(example = "1")
    private long userId;
}

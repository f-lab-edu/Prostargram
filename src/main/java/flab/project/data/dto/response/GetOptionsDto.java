package flab.project.data.dto.response;

import flab.project.data.enums.PublicScope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "유저의 설정 상태")
public class GetOptionsDto {
    @Schema(description = "개정 공개/비공개 여부")
    private PublicScope publicScope;
}

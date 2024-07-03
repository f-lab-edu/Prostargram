package flab.project.domain.user.model;

import flab.project.domain.user.enums.PublicScope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "유저의 설정 상태")
public class Settings {
    @Schema(description = "개정 공개/비공개 여부")
    private PublicScope publicScope;
}
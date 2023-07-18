package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "이메일 인증 코드 결과 Dto")
public class CreateEmailAuthCodeResponseDto {

    @Schema(example = "example@example.com", nullable = false)
    private final String email;

    public CreateEmailAuthCodeResponseDto(String email) {
        this.email = email;
    }
}

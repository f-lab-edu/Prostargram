package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "이메일 인증코드 Dto")
public class CreateEmailAuthCodeRequestDto {

    @Schema(example = "example@example.com", nullable = false)
    private final String email;

    public CreateEmailAuthCodeRequestDto(String email) {
        this.email = email;
    }
}

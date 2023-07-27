package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "이메일 인증을 위한 이메일 생성 Dto")
public class CreateEmailRequestDto {

    @Schema(example = "example@example.com", nullable = false)
    private final String email;

    public CreateEmailRequestDto(String email) {
        this.email = email;
    }
}

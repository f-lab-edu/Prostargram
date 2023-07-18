package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "이메일 인증 결과 Dto")
public class GetEmailResponseDto {

    @Schema(example = "example@example.com")
    private final String email;

    public GetEmailResponseDto(String email) {
        this.email = email;
    }
}

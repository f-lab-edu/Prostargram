package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "이메일 인증 Dto")
public class GetEmailRequestDto {

    @Schema(example = "example@example.com", nullable = false)
    private final String email;

    @Schema(example = "123456", description = "이메일로 전송한 인증 번호", nullable = false)
    private final String authCode;

    public GetEmailRequestDto(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }
}

package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "이메일 인증을 위한 인증코드를 가져오는 Dto")
public class GetEmailAuthCodeRequestDto {

    @Schema(example = "123456", description = "이메일로 전송한 인증 코드", nullable = false)
    private final String authCode;

    public GetEmailAuthCodeRequestDto(String authCode) {
        this.authCode = authCode;
    }
}

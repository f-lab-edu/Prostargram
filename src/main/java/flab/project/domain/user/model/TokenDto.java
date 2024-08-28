package flab.project.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {

    @Schema(
            description = "유효기간 30분",
            example = "eyJhbGciOiJIUzUxMiJ9..."
    )
    private final String accessToken;

    @Schema(
            description = "유효기간 14일",
            example = "eyJhbGciOiJIUzUxMiJ9..."
    )
    private final String refreshToken;
}
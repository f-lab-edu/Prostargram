package flab.project.domain.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {
    private final String accessToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String refreshToken;
}
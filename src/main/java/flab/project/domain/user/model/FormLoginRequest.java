package flab.project.domain.user.model;

import flab.project.common.annotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FormLoginRequest {

    @Schema(example = "example@example.com")
    @Email
    @NotBlank
    private String email;

    @Schema(example = "Password123!!@")
    @Password
    @NotBlank
    private String password;
}
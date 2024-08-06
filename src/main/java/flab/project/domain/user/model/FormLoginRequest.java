package flab.project.domain.user.model;

import flab.project.common.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FormLoginRequest {

    @Email
    @NotBlank
    private String email;

    @Password
    @NotBlank
    private String password;
}
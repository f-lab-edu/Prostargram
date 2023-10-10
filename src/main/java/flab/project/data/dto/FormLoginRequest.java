package flab.project.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FormLoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    // todo password검증하는 annotation이 있으면 좋겠는뎅...
    private String password;
}

package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 가입 - 필수 정보 Dto")
public class SignUp {

    @Schema(example = "example@example.com")
    @Email
    @NotBlank
    private String email;

    @Schema(example = "password", description = "비밀번호는 영문 대소문자, 숫자, 특수 문자가 각각 하나 이상 포함되어야 한다.")
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @Schema(example = "이은비")
    @NotBlank
    @Size(min = 1, max = 16)
    private String userName;
}

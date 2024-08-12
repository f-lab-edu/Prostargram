package flab.project.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 가입 DTO")
public class SignUp {

    @Schema(example = "example@example.com")
    @Email(message = "올바른 이메일 형식이여야 합니다.")
    @NotBlank(message = "올바른 이메일 형식이여야 합니다.")
    private String email;

    @Schema(example = "Password123!!@", description = "비밀번호는 영문 대소문자, 숫자, 특수 문자가 각각 하나 이상 포함되어야 한다.")
    @NotBlank(message = "비밀번호 형식을 확인해 주세요.")
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;

    @Schema(example = "이은비")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 16, message = "닉네임의 최대 길이는 16자입니다.")
    private String username;

    @Schema(example = "token12", description = "인증 코드 검증 API를 통해 받은 토큰")
    @NotBlank(message = "이메일 인증을 다시 진행해 주세요.")
    String emailToken;

    @Schema(example = "token12", description = "닉네임 중복 검증 API를 통해 받은 토큰")
    @NotBlank(message = "닉네임 중복 검증을 다시 진행해주세요.")
    String usernameToken;
}

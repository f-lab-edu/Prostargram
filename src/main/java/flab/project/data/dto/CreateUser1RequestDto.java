package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원 가입 - 필수 정보 Dto")
public class CreateUser1RequestDto {

    @Schema(example = "example@example.com", nullable = false)
    private final String email;

    @Schema(example = "password", description = "비밀번호는 영문 대소문자, 숫자, 특수 문자가 각각 하나 이상 포함되어야 한다.", nullable = false, minLength = 8, maxLength = 20)
    private final String password;

    @Schema(example = "이은비")
    private final String userName;

    public CreateUser1RequestDto(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}

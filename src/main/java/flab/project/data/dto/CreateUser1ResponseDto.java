package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원 가입 - 필수 정보 결과 Dto")
public class CreateUser1ResponseDto {

    @Schema(example = "example@example.com")
    private final String email;

    @Schema(example = "이은비")
    private final String userName;

    public CreateUser1ResponseDto(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }
}

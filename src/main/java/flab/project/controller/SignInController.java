package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.SignInService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class SignInController {

    private final SignInService signInService;

    // Todo Dto로 빼는 것도 나쁘지 않을듯, 근데 Controller -> Service만을 위해 Dto를 만드는게 맞나?
    // Todo 비밀번호 필터링에 대한 커스텀 어노테이션 추가 예정
    @Operation(summary = "회원가입 API - 1단계")
    @Parameters({
            @Parameter(name = "email", description = "유저의 이메일", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "emailToken", description = "이메일 검증 토큰", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "userName", description = "유저의 닉네임", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "userNameToken", description = "닉네임 검증 토큰", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "password", description = "유저의 비밀번호", in = ParameterIn.QUERY, required = true)})
    @PostMapping(value = "/users/step1")
    public SuccessResponse addUserInformation(@RequestParam("email") @Email @NotBlank String email,
                                              @RequestParam("emailToken") @NotBlank String emailToken,
                                              @RequestParam("userName") @NotBlank @Size(min = 1, max = 16) String userName,
                                              @RequestParam("userNameToken") @NotBlank String userNameToken,
                                              @RequestParam("password") @NotBlank @Size(min = 8, max = 20) String password) {
        signInService.addUserInformation(email, emailToken, userName, userNameToken, password);

        return new SuccessResponse<>();
    }
}

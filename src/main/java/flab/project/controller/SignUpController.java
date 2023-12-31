package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.SignUp;
import flab.project.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class SignUpController {

    private final SignUpService signUpService;

    // Todo 비밀번호 필터링에 대한 커스텀 어노테이션 추가 예정
    @Operation(summary = "회원가입 API - 1단계")
    @Parameters({
            @Parameter(name = "emailToken", description = "이메일 검증 토큰", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "userNameToken", description = "닉네임 검증 토큰", in = ParameterIn.QUERY, required = true)})
    @PostMapping(value = "/users")
    public SuccessResponse addUser(@RequestBody @Valid SignUp signUp,
                                              @RequestParam @NotBlank String emailToken,
                                              @RequestParam @NotBlank String userNameToken) {
        signUpService.addUser(signUp, emailToken, userNameToken);

        return new SuccessResponse<>();
    }
}

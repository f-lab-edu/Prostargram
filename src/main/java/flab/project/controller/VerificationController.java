package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class VerificationController {

    private final VerificationService verificationService;

    @Operation(summary = "인증코드 전송 API")
    @PostMapping(value = "/verification/email")
    public SuccessResponse sendVerificationCode(@RequestParam("address") @Email @NotBlank String email) {
        verificationService.sendVerificationCode(email);

        return new SuccessResponse();
    }

    @Operation(summary = "인증코드 검증 API")
    @Parameter(name = "code", description = "유저의 이메일에 발송된 인증코드", required = true)
    @PostMapping(value = "/verification/email/{address}")
    public SuccessResponse checkVerificationCode(@PathVariable("address") @Email @NotBlank String email,
                                                 @RequestParam("code") @NotBlank String code) {
        verificationService.checkVerificationCode(email, code);

        return new SuccessResponse<>();
    }
}
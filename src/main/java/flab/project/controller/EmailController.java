package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "인증코드 전송 API")
    @Parameter(name = "email", description = "유저의 이메일", required = true)
    @PostMapping(value = "/email")
    public SuccessResponse sendVerificationCode(@RequestParam("email") @Email @NotBlank String email) {
        emailService.sendVerificationCode(email);

        return new SuccessResponse();
    }
}
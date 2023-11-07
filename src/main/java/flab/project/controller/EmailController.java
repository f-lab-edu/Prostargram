package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping(value = "/verification/email")
    public SuccessResponse sendVerificationCode(@RequestParam("address") @Email @NotBlank String email) {
        emailService.sendVerificationCode(email);

        return new SuccessResponse();
    }
}
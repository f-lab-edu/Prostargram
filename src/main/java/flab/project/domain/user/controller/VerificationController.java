package flab.project.domain.user.controller;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.stream.Collectors;

@Tag(name = "회원가입 API", description = "회원가입 과정에서 사용되는 API</br>" +
        "[회원가입 전체로직](https://www.notion.so/b577233726be48ab9706381c8cbcd7ff?pvs=4)은 링크를 참고하길 바란다.")
@Validated
@RequiredArgsConstructor
@RestController
public class VerificationController {

    private final VerificationService verificationService;

    @ExceptionHandler(ConstraintViolationException.class)
    public FailResponse handleConstraintViolationException(ConstraintViolationException exception) {
        String errorMessage = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return new FailResponse(errorMessage, 4000);
    }

    @Operation(summary = "인증코드 전송 API", description = "인증코드 전송 API이다.</br>" +
            "유저가 전달한 email로 이메일 인증코드가 전달되며 해당 인증코드는 **인증코드 검증 API**에서 사용된다.")
    @PostMapping(value = "/verification/email")
    @Parameter(
            name = "email",
            required = true,
            description = "해당 email로 인증 코드가 보내진다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "이메일 인증코드 전송 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"isSuccess\": true," +
                                            "\"code\": 1000," +
                                            "\"message\": \"요청에 성공하였습니다.\"" +
                                            "}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "올바른 이메일 형식이여야 합니다.(이메일 형식과 일치하지 않거나 이메일이 전송되지 않은 경우)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"isSuccess\": false," +
                                            "\"code\": 4000," +
                                            "\"message\": \"올바른 이메일 형식이여야 합니다.\"" +
                                            "}"
                            )
                    )
            ),
    })
    public SuccessResponse<Void> sendVerificationCode(
            @RequestParam("email")
            @Email(message = "올바른 이메일 형식이여야 합니다.")
            @NotBlank(message = "올바른 이메일 형식이여야 합니다.") String email
    ) {
        verificationService.sendVerificationCode(email);

        return new SuccessResponse<>();
    }

    @Operation(summary = "인증코드 검증 API")
    @Parameter(name = "code", description = "유저의 이메일에 발송된 인증코드", required = true)
    @PostMapping(value = "/verification/email/{address}")
    public SuccessResponse<Void> checkVerificationCode(@PathVariable("address") @Email @NotBlank String email,
                                                 @RequestParam("code") @NotBlank String code) {
        verificationService.checkVerificationCode(email, code);

        return new SuccessResponse<>();
    }
}
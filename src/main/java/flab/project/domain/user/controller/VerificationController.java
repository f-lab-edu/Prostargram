package flab.project.domain.user.controller;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.exception.ExistedAccountException;
import flab.project.domain.user.exception.InvalidVerificationCodeException;
import flab.project.domain.user.model.EmailTokenReturnDto;
import flab.project.domain.user.service.VerificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.stream.Collectors;

@Tag(name = "회원가입 API", description = "회원가입 과정에서 사용되는 API</br>" +
        "[회원가입 전체로직](https://github.com/f-lab-edu/Prostargram/wiki/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85-%EC%A0%84%EC%B2%B4-%EB%A1%9C%EC%A7%81)은 링크를 참고하시길 바랍니다.")
@Validated
@RequiredArgsConstructor
@RestController
public class VerificationController {

    private final VerificationService verificationService;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public FailResponse handleConstraintViolationException(ConstraintViolationException exception) {
        String errorMessage = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return new FailResponse(errorMessage, 4000);
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(ExistedAccountException.class)
    public FailResponse handleExistedAccountException(ExistedAccountException exception) {
        String exceptionMessage = exception.getMessage();

        return new FailResponse(exceptionMessage, 4000);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidVerificationCodeException.class)
    public FailResponse handleInvalidVerificationCodeException(InvalidVerificationCodeException exception) {
        String exceptionMessage = exception.getMessage();

        return new FailResponse(exceptionMessage, 4000);
    }

    @Operation(summary = "인증코드 전송 API", description = "인증코드 전송 API이다.</br>" +
            "유저가 전달한 email로 이메일 인증코드가 전달되며 해당 인증코드는 **인증코드 검증 API**에서 사용된다.</br>" +
            "Reference: [참고 문서](https://github.com/f-lab-edu/Prostargram/wiki/%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D-%EC%BD%94%EB%93%9C-%EC%A0%84%EC%86%A1-API)")
    @PostMapping(value = "/verification/email")
    @Parameter(
            name = "email",
            required = true,
            description = "인증 코드가 보내질 이메일"
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
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 가입된 계정입니다.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = "{" +
                                            "\"isSuccess\": false," +
                                            "\"code\": 4000," +
                                            "\"message\": \"이미 가입된 계정입니다.\"" +
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

    @Operation(
            summary = "인증 코드 검증 API",
            description = "유저의 이메일에 발송된 인증 코드를 통해 유저의 이메일이 맞는지 확인하는 API이다.</br>" +
                    "Reference: [참고 문서](https://github.com/f-lab-edu/Prostargram/wiki/%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D-%EC%BD%94%EB%93%9C-%EA%B2%80%EC%A6%9D-API)"
    )
    @Parameters(value = {
            @Parameter(
                    name = "email",
                    description = "인증 코드가 발송된 유저의 이메일",
                    required = true
            ),
            @Parameter(
                    name = "code",
                    description = "유저의 이메일에 발송된 인증 코드",
                    required = true
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "인증 코드 검증 성공",
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
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = {
                                @ExampleObject(
                                    name = "올바른 이메일 형식이여야 합니다.",
                                    description = "올바른 이메일 형식이여야 합니다.(이메일 형식과 일치하지 않거나 이메일이 전송되지 않은 경우)",
                                    value = "{" +
                                        "\"isSuccess\": false," +
                                        "\"code\": 4000," +
                                        "\"message\": \"올바른 이메일 형식이여야 합니다.\"" +
                                        "}"
                                ),
                                @ExampleObject(
                                    name = "인증코드를 입력해 주세요.",
                                    description = "인증 코드를 입력하지 않았거나, 공백 문자열(\"    \"), 빈 문자열(\"\")로 이루어진 경우",
                                    value = "{" +
                                        "\"isSuccess\": false," +
                                        "\"code\": 4000," +
                                        "\"message\": \"인증코드를 입력해 주세요.\"" +
                                        "}"
                                ),
                                @ExampleObject(
                                    name = "인증 코드가 일치하지 않습니다.",
                                    description = "인증 코드가 일치하지 않는 경우",
                                    value = "{" +
                                        "\"isSuccess\": false," +
                                        "\"code\": 4000," +
                                        "\"message\": \"인증 코드가 일치하지 않습니다.\"" +
                                        "}"
                                )
                            }
                    )
            ),
    })
    @PostMapping(value = "/verification/email/{email}")
    public SuccessResponse<EmailTokenReturnDto> checkVerificationCode(
            @PathVariable("email")
            @Email(message = "올바른 이메일 형식이여야 합니다.")
            @NotBlank(message = "올바른 이메일 형식이여야 합니다.") String email,
            @RequestParam("code")
            @NotBlank(message = "인증 코드를 입력해주세요") String code
    ) {
        EmailTokenReturnDto emailToken = verificationService.checkVerificationCode(email, code);

        return new SuccessResponse<>(emailToken);
    }
}
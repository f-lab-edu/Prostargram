package flab.project.domain.user.controller;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.exception.InvalidEmailTokenException;
import flab.project.domain.user.exception.InvalidUsernameTokenException;
import flab.project.domain.user.model.SignUp;
import flab.project.domain.user.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입 API")
@Validated
@RequiredArgsConstructor
@RestController
public class SignUpController {

    private final SignUpService signUpService;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public FailResponse handleConstraintViolationException(MethodArgumentNotValidException exception) {
        String exceptionMessage = exception.getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining());

        return new FailResponse(exceptionMessage, 4000);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        InvalidEmailTokenException.class,
        InvalidUsernameTokenException.class
    })
    public FailResponse handleInvalidTokenException(RuntimeException exception) {
        return new FailResponse(exception.getMessage(), 4000);
    }

    // Todo 비밀번호 필터링에 대한 커스텀 어노테이션 추가 예정
    @Operation(
        summary = "회원가입 API",
        description =
            """
                회원가입 페이지에서 사용되는 API이다.
                사전에 아래 API들이 모두 선행되어야 한다.
                1. 이메일 인증 코드 전송 요청 API
                2. 이메일 인증 코드 확인 요청 API
                3. 닉네임 중복 확인 요청 API
                                
                Reference: [참고 문서](https://github.com/f-lab-edu/Prostargram/wiki/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85-%EC%A0%84%EC%B2%B4-%EB%A1%9C%EC%A7%81)
                """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "회원가입 성공",
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
            description = "요청 실패",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = FailResponse.class),
                examples = {
                    @ExampleObject(
                        name = "닉네임을 입력해주세요.",
                        description = ""
                            + "1. 닉네임이 빈 문자열인 경우 (\\\"\\\")\n"
                            + "2. 닉네임이 공백 문자로 이루어진 경우 (\\\"     \\\")",
                        value = "{" +
                            "\"isSuccess\": false," +
                            "\"code\": 4000," +
                            "\"message\": \"닉네임을 입력해주세요.\"" +
                            "}"
                    ),
                    @ExampleObject(
                        name = "닉네임의 최대 길이는 16자입니다.",
                        description = "닉네임의 최대 길이(16)를 초과한 경우</br>",
                        value = "{" +
                            "\"isSuccess\": false," +
                            "\"code\": 4000," +
                            "\"message\": \"닉네임의 최대 길이는 16자입니다.\"" +
                            "}"
                    ),
                    @ExampleObject(
                        name = "올바른 이메일 형식이여야 합니다.",
                        description = "이메일 형식이 잘못 된 경우",
                        value = "{" +
                            "\"isSuccess\": false," +
                            "\"code\": 4000," +
                            "\"message\": \"올바른 이메일 형식이여야 합니다.\"" +
                            "}"
                    ),
                    @ExampleObject(
                        name = "비밀번호 형식을 확인해 주세요.",
                        description = "비밀번호 형식이 잘못 된 경우",
                        value = "{" +
                            "\"isSuccess\": false," +
                            "\"code\": 4000," +
                            "\"message\": \"비밀번호 형식을 확인해 주세요.\"" +
                            "}"
                    ),
                    @ExampleObject(
                        name = "이메일 토큰 유효기간 초과",
                        description = ""
                            + "1. 이메일 토큰 유효기간 초과\n"
                            + "2. 잘못된 이메일 토큰이 전달된 경우\n",
                        value = "{" +
                            "\"isSuccess\": false," +
                            "\"code\": 4000," +
                            "\"message\": \"이메일 인증을 다시 진행해 주세요.\"" +
                            "}"
                    ),
                    @ExampleObject(
                        name = "닉네임 토큰 유효기간 초과",
                        description = ""
                            + "1. 닉네임 토큰 유효기간 초과\n"
                            + "2. 잘못된 닉네임 토큰이 전달된 경우\n",
                        value = "{" +
                            "\"isSuccess\": false," +
                            "\"code\": 4000," +
                            "\"message\": \"닉네임 중복 검증을 다시 진행해주세요.\"" +
                            "}"
                    ),
                }
            )
        ),
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(value = "/users")
    public SuccessResponse<Void> addUser(@RequestBody @Validated SignUp signUp) {
        signUpService.addUser(signUp);

        return new SuccessResponse<>();
    }
}

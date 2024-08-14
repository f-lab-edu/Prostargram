package flab.project.domain.user.controller;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.exception.ExistedUsernameException;
import flab.project.domain.user.model.UsernameTokenReturnDto;
import flab.project.domain.user.service.UsernameDuplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입 API")
@Validated
@RequiredArgsConstructor
@RestController
// Todo Duplication 대신 다른 명사로 클래스 이름 변경 예정
public class UsernameDuplicationController {

    private final UsernameDuplicationService usernameDuplicationService;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public FailResponse handleConstraintViolationException(ConstraintViolationException exception) {
        String errorMessage = exception.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));

        return new FailResponse(errorMessage, 4000);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserInputException.class)
    public FailResponse handleInvalidUserInputException(InvalidUserInputException exception) {
        String exceptionMessage = exception.getMessage();

        return new FailResponse(exceptionMessage, 4000);
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(ExistedUsernameException.class)
    public FailResponse handleInvalidUserInputException(ExistedUsernameException exception) {
        String exceptionMessage = exception.getMessage();

        return new FailResponse(exceptionMessage, 4000);
    }

    // Todo 욕설 및 비속어 필터링은 커스텀 어노테이션을 만들 예정
    @Operation(
        summary = "닉네임 중복 검증 API",
        description = "닉네임 중복 여부를 검사한다.</br>"
            + "사용 가능한 닉네임이라는 응답을 받으면 15분간 다른 유저는 해당 닉네임으로 회원가입을 할 수 없다.</br>"
            + "Reference: [참고 문서](https://github.com/f-lab-edu/Prostargram/wiki/%EB%8B%89%EB%84%A4%EC%9E%84-%EC%A4%91%EB%B3%B5-%ED%99%95%EC%9D%B8-API)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "닉네임 중복 검증 성공",
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
            description = "유효하지 않은 닉네임인 경우",
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
                        description = "닉네임의 최대 길이(16)자를 초과한 경우</br>",
                        value = "{" +
                            "\"isSuccess\": false," +
                            "\"code\": 4000," +
                            "\"message\": \"닉네임의 최대 길이는 16자입니다.\"" +
                            "}"
                    ),
                }
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "이미 존재하는 닉네임 입니다.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = FailResponse.class),
                examples = @ExampleObject(
                    value = "{" +
                        "\"isSuccess\": false," +
                        "\"code\": 4000," +
                        "\"message\": \"이미 존재하는 닉네임 입니다.\"" +
                        "}"
                )
            )
        ),
    })
    @Parameter(
        name = "username",
        description = "유저의 닉네임",
        in = ParameterIn.QUERY,
        required = true
    )
    @PostMapping(value = "/verification/username")
    public SuccessResponse<UsernameTokenReturnDto> verifyDuplicateUserName(
        @RequestParam("username")
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 1, max = 16, message = "닉네임의 최대 길이는 16자입니다.")
        String username
    ) {
        UsernameTokenReturnDto usernameToken = usernameDuplicationService.verifyDuplicateUserName(username);

        return new SuccessResponse<>(usernameToken);
    }
}

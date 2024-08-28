package flab.project.domain.user.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.model.FormLoginRequest;
import flab.project.domain.user.model.TokenDto;
import flab.project.domain.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API")
@Validated
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({
            BadCredentialsException.class,
            MethodArgumentNotValidException.class
    })
    public FailResponse handleConstraintViolationException(Exception exception) {
        return new FailResponse("아이디 혹은 비밀번호가 틀렸습니다.", 4000);
    }

    @Operation(
            summary = "로그인 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다.",
                                                "result": {
                                                    "accessToken": "eyJhbGciOi~",
                                                    "refreshToken": "eyJhbGciOi~"
                                                }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    name = "아이디 혹은 비밀번호가 틀렸습니다.",
                                    value = "{" +
                                            "\"isSuccess\": false," +
                                            "\"code\": 4000," +
                                            "\"message\": \"아이디 혹은 비밀번호가 틀렸습니다.\"" +
                                            "}"
                            )
                    )
            )
    })
    @PostMapping("/login")
    public SuccessResponse<TokenDto> formLogin(@Validated @RequestBody FormLoginRequest formLoginRequest) {
        TokenDto tokenDto = authService.formLogin(formLoginRequest);

        return new SuccessResponse<>(tokenDto);
    }

    @Operation(
            summary = "토큰 재발행 API"
    )
    @PostMapping("/reissue")
    public SuccessResponse<TokenDto> reissue(
            @LoggedInUserId Long userId,
            Authentication authentication
    ) {
        TokenDto tokenDto = authService.reissue(userId, authentication);

        return new SuccessResponse<>(tokenDto);
    }

    @GetMapping("/logout/success")
    public SuccessResponse<Void> returnLogoutResponse() {
        return new SuccessResponse<>();
    }
}
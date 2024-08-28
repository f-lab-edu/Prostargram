package flab.project.domain.user.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NumberLimitOfSocialAccountsExceededException;
import flab.project.domain.user.exception.SocialAccountNotFoundException;
import flab.project.domain.user.model.UpdateSocialAccountRequestDto;
import flab.project.domain.user.facade.SocialAccountFacade;
import flab.project.domain.user.service.SocialAccountService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "소셜 계정")
@Validated
@RequiredArgsConstructor
@RestController
public class SocialAccountController {

    private final SocialAccountFacade socialAccountFacade;
    private final SocialAccountService socialAccountService;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            NumberLimitOfSocialAccountsExceededException.class
    })
    public FailResponse handleExceedNumberOfSocialAccountException(
            NumberLimitOfSocialAccountsExceededException exception
    ) {
        return new FailResponse(exception.getMessage(), 4000);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            SocialAccountNotFoundException.class
    })
    public FailResponse handleSocialAccountNotFoundException(SocialAccountNotFoundException exception) {
        return new FailResponse(exception.getMessage(), 4000);
    }

    @Operation(
            summary = "소셜 계정 추가"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "소셜 계정 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "소셜 계정 최대 개수 초과",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4000,
                                                "message": "소셜 계정은 최대 3개 까지 설정할 수 있습니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/social-accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> addSocialAccount(
            @LoggedInUserId Long userId,
            @Validated @RequestBody UpdateSocialAccountRequestDto updateSocialAccount
    ) {
        updateSocialAccount.setUserId(userId);

        socialAccountFacade.addSocialAccount(updateSocialAccount);

        return new SuccessResponse<>();
    }

    @Operation(
            summary = "소셜 계정 삭제"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "소셜 계정 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "삭제 요청을 하는 소셜 계정이 등록된 소셜계정이 아닌 경우.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4000,
                                                "message": "존재하지 않는 소셜 계정입니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @DeleteMapping("/social-accounts")
    public SuccessResponse<Void> deleteSocialAccount(
            @LoggedInUserId Long userId,
            @Validated @RequestBody UpdateSocialAccountRequestDto updateSocialAccount
    ) {
        updateSocialAccount.setUserId(userId);

        socialAccountService.deleteSocialAccount(updateSocialAccount);
        return new SuccessResponse<>();
    }
}
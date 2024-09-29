package flab.project.domain.user.controller;

import static flab.project.utils.AccessManagementUtil.assertUserIdOwner;

import flab.project.common.FileStorage.UploadedFileUrl;
import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.model.Profile;
import flab.project.domain.user.enums.GetProfileRequestType;

import flab.project.domain.user.model.UpdateProfileRequestDto;
import flab.project.domain.user.facade.UserFacade;
import flab.project.domain.user.service.UserService;
import flab.project.utils.AccessManagementUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "프로필")
@Validated
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;

    @Operation(
            summary = "프로필 수정 페이지 정보 확인하기 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인된 유저와 pathVariable로 전달받은 userId가 다른 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 403,
                                                "message": "권한이 없습니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping(value = "/users/{userId}/profile_update_page")
    public SuccessResponse<Profile> getProfileUpdatePageInfo(
            @LoggedInUserId Long loggedInUserId,
            @PathVariable("userId") @Positive long userIdFromPathVariable
    ) {
        assertUserIdOwner(loggedInUserId, userIdFromPathVariable);

        Profile profile = userService.getProfileInfo(loggedInUserId, GetProfileRequestType.UPDATE);

        return new SuccessResponse<>(profile);
    }

    @Operation(
            summary = "프로필 페이지 정보 확인하기 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "404",
                    description = "PathVariable의 userId가 존재하지 않는 유저인 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4001,
                                                "message": "존재하지 않는 유저입니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping(value = "/users/{userId}/profile_page")
    public SuccessResponse<Profile> getProfilePageInfo(
            @PathVariable("userId") @Positive long userId
    ) {
        Profile profile = userService.getProfileInfo(userId, GetProfileRequestType.GET);

        return new SuccessResponse<>(profile);
    }

    @Operation(
            description = "프로필 수정하기 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "프로필 수정 하기",
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
                    responseCode = "403",
                    description = "로그인된 유저와 pathVariable로 전달받은 userId가 다른 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 403,
                                                "message": "권한이 없습니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping(value = "/users/{userId}/profile-info")
    public SuccessResponse<Void> updateProfile(
            @LoggedInUserId Long loggedInUserId,
            @PathVariable("userId") @Positive long userIdFromPathVariable,
            @Validated @RequestBody UpdateProfileRequestDto updateProfileRequestDto
    ) {
        assertUserIdOwner(loggedInUserId, userIdFromPathVariable);

        userService.updateProfile(loggedInUserId, updateProfileRequestDto);

        return new SuccessResponse<>();
    }

    @Operation(
            summary = "프로필 이미지 수정하기 API",
            description = "1. 해당 API는 <b>수정 예정</b>입니다. 추후, 서버에 직접 이미지를 올리는 것이 아닌"
                    + " preassignedURL방식으로 API를 수정할 예정입니다.\n"
                    + "2. 현재 허용되는 이미지 확장자는 png, jpg, jpeg입니다. 추후 프론트,백간의 논의 후 어떤 확장자를"
                    + "지원할지 결정할 예정입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "프로필 수정 하기",
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
                    responseCode = "403",
                    description = "로그인된 유저와 pathVariable로 전달받은 userId가 다른 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 403,
                                                "message": "권한이 없습니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @PatchMapping(value = "/users/{userId}/profile-image")
    public SuccessResponse<UploadedFileUrl> updateProfileImage(
            @LoggedInUserId Long loggedInUserId,
            @PathVariable("userId") @Positive long userIdFromPathVariable
    ) {
        AccessManagementUtil.assertUserIdOwner(loggedInUserId, userIdFromPathVariable);

        UploadedFileUrl uploadedFileUrl = userFacade.updateProfileImage(loggedInUserId);

        return new SuccessResponse<>(uploadedFileUrl);
    }
}
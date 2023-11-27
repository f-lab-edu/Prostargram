package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.PostWithUser;
import flab.project.data.dto.Settings;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.facade.UserFacade;
import flab.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class UserController {

    private static final long MAX_LIMIT = 10;

    private final UserService userService;
    private final UserFacade userFacade;

    @Operation(
            summary = "프로필 수정 페이지 정보 확인하기 API"
    )
    @GetMapping(value = "/users/{userId}/profile_update_page")
    public SuccessResponse getProfileUpdatePageInfo(
            @PathVariable("userId") @Positive long userId
    ) {
        return userService.getProfileInfo(userId, GetProfileRequestType.UPDATE);
    }

    @Operation(
            summary = "프로필 페이지 정보 확인하기 API"
    )
    @GetMapping(value = "/users/{userId}/profile_page")
    public SuccessResponse<Profile> getProfilePageInfo(
            @PathVariable("userId") @Positive long userId
    ) {
        return userService.getProfileInfo(userId, GetProfileRequestType.GET);
    }

    @Operation(
            summary = "개인 설정 상태 확인하기 API"
    )
    @Parameters(
            value = {
                    @Parameter(name = "userId", description = "설정 상태를 확인하고자 하는 유저의 id", required = true),
            }
    )
    @GetMapping(value = "/users/{userId}/options")
    public Settings getPersonalSettings(
            @PathVariable("userId") Long userId
    ) {
        return null;
    }

    @Operation(
            summary = "옵션을 수정하는 API"
    )
    @Parameters(
            value = {
                    @Parameter(name = "userId", description = "설정 상태를 변경하고자 하는 유저의 id", required = true),
            }
    )
    @PatchMapping(value = "/users/{userId}/options")
    public String updatePersonalSettings(
            @PathVariable("userId") Long userId,
            Settings updateOptionsRequestDto
    ) {
        return "test";
    }

    @PatchMapping(value = "/users/{userId}/profile-info")
    public SuccessResponse updateProfile(
            @PathVariable("userId") @Positive long userId,
            @Validated @RequestBody UpdateProfileRequestDto updateProfileRequestDto
    ) {
        return userService.updateProfile(userId, updateProfileRequestDto);
    }

    @Operation(
            summary = "프로필 이미지 수정하기 API"
    )
    @PatchMapping(value = "/users/{userId}/profile-image")
    public SuccessResponse updateProfileImage(
            @PathVariable("userId") @Positive long userId,
            @RequestPart(value = "profileImage") MultipartFile profileImg
    ) {
        return userFacade.updateProfileImage(userId, profileImg);
    }

    @Operation(summary = "프로필 피드 가져오기 API")
    @Parameters({@Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.QUERY, required = true), @Parameter(name = "lastProfilePostId", description = "가장 마지막으로 받아온 프로필 게시물의 id", in = ParameterIn.QUERY, required = true), @Parameter(name = "limit", description = "한 번에 조회할 프로필 피드의 개수", in = ParameterIn.QUERY, required = true)})
    @GetMapping(value = "users/{userId}/profile-feed")
    public SuccessResponse<List<PostWithUser>> getProfileFeed(@PathVariable("userId") @Positive long userId,
                                                              @RequestParam("postId") @Positive long postId,
                                                              @RequestParam(value = "lastProfilePostId", required = false) @Positive Long lastProfilePostId,
                                                              @RequestParam(value = "limit", defaultValue = "10") @Positive long limit) {
        if (limit > MAX_LIMIT) {
            throw new InvalidUserInputException("Invalid limit.");
        }

        List<PostWithUser> profileFeed = userService.getProfileFeed(userId, postId, lastProfilePostId, limit);

        return new SuccessResponse<>(profileFeed);
    }
}
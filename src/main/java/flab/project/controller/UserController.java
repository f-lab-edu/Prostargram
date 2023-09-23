package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.*;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;

import flab.project.facade.UserFacade;
import flab.project.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RequiredArgsConstructor
@RestController
public class UserController {

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
}
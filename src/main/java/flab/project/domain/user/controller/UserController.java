package flab.project.domain.user.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.model.Profile;
import flab.project.domain.user.enums.GetProfileRequestType;

import flab.project.domain.user.model.UpdateProfileRequestDto;
import flab.project.domain.user.facade.UserFacade;
import flab.project.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping(value = "/users/{userId}/profile_update_page")
    public SuccessResponse<Profile> getProfileUpdatePageInfo(
            @PathVariable("userId") @Positive long userId
    ) {
        Profile profile = userService.getProfileInfo(userId, GetProfileRequestType.UPDATE);

        return new SuccessResponse<>(profile);
    }

    @Operation(
            summary = "프로필 페이지 정보 확인하기 API"
    )
    @GetMapping(value = "/users/{userId}/profile_page")
    public SuccessResponse<Profile> getProfilePageInfo(
            @PathVariable("userId") @Positive long userId
    ) {
        Profile profile = userService.getProfileInfo(userId, GetProfileRequestType.GET);

        return new SuccessResponse<>(profile);
    }

    @PatchMapping(value = "/users/{userId}/profile-info")
    public SuccessResponse<Void> updateProfile(
            @PathVariable("userId") @Positive long userId,
            @Validated @RequestBody UpdateProfileRequestDto updateProfileRequestDto
    ) {
        userService.updateProfile(userId, updateProfileRequestDto);

        return new SuccessResponse<>();
    }

    @Operation(
            summary = "프로필 이미지 수정하기 API"
    )
    @PatchMapping(value = "/users/{userId}/profile-image")
    public SuccessResponse<Void> updateProfileImage(
            @PathVariable("userId") @Positive long userId,
            @RequestPart(value = "profileImage") MultipartFile profileImg
    ) {
        userFacade.updateProfileImage(userId, profileImg);

        return new SuccessResponse<>();
    }
}
package flab.project.domain.user.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.enums.ScreenMode;
import flab.project.domain.user.model.Settings;
import flab.project.domain.user.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import static flab.project.domain.user.enums.PublicScope.PRIVATE;
import static flab.project.domain.user.enums.PublicScope.PUBLIC;

@Validated
@RequiredArgsConstructor
@RestController
public class SettingController {

    private final SettingService settingService;

    @Operation(summary = "라이트/다크 모드 수정 하기 API")
    @Parameter(name = "userId", description = "설정 상태를 확인 하고자 하는 유저의 id", required = true)
    @PatchMapping("/users/{userId}/settings")
    public SuccessResponse<Void> updateScreenMode(
            @PathVariable("userId") @Positive long userId,
            @RequestParam("screen-mode") ScreenMode screenMode,
            HttpServletResponse httpServletResponse
    ) {
        settingService.updateScreenMode(userId, screenMode, httpServletResponse);

        return new SuccessResponse<>();
    }

    @Operation(summary = "개인 설정 상태 확인하기 API")
    @Parameter(name = "userId", description = "설정 상태를 확인하고자 하는 유저의 id", required = true)
    @GetMapping("/users/settings")
    public SuccessResponse<Settings> getPersonalSettings(
            @LoggedInUserId Long userId
    ) {
        Settings personalSettings = settingService.getPersonalSettings(userId);

        return new SuccessResponse<>(personalSettings);
    }

    @PatchMapping("/users/{userId}/settings/public-scope/public")
    public SuccessResponse<Void> updateUserPublicScopeToPublic(
            @PathVariable("userId") @Positive long userId
    ) {
        settingService.updateUserPublicScope(userId, PUBLIC);

        return new SuccessResponse<>();
    }

    @PatchMapping("/users/{userId}/settings/public-scope/private")
    public SuccessResponse<Void> updateUserPublicScopeToPrivate(
            @PathVariable("userId") @Positive long userId
    ) {
        settingService.updateUserPublicScope(userId, PRIVATE);

        return new SuccessResponse<>();
    }
}
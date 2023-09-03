package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import static flab.project.data.enums.PublicScope.PRIVATE;
import static flab.project.data.enums.PublicScope.PUBLIC;

@Validated
@RequiredArgsConstructor
@RestController
public class SettingController {

    private final SettingService settingService;

    @Operation(summary = "개인 설정 상태 확인하기 API")
    @Parameters(value = {
            @Parameter(name = "userId", description = "설정 상태를 확인하고자 하는 유저의 id", required = true)})
    @GetMapping("/users/{userId}/settings")
    public SuccessResponse getPersonalSettings(@PathVariable("userId") @Positive long userId) {
        return settingService.getPersonalSettings(userId);
    }

    @PatchMapping("/users/{userId}/settings/public-scope/public")
    public SuccessResponse updateUserPublicScopeToPublic(
            @PathVariable("userId") @Positive long userId
    ) {
        return settingService.updateUserPublicScope(userId, PUBLIC);
    }

    @PatchMapping("/users/{userId}/settings/public-scope/private")
    public SuccessResponse updateUserPublicScopeToPrivate(
            @PathVariable("userId") @Positive long userId
    ) {
        return settingService.updateUserPublicScope(userId, PRIVATE);
    }
}

package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.PublicScope;
import flab.project.service.SettingService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static flab.project.data.enums.PublicScope.PRIVATE;
import static flab.project.data.enums.PublicScope.PUBLIC;

@Validated
@RequiredArgsConstructor
@RestController
public class SettingController {

    private final SettingService settingService;

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

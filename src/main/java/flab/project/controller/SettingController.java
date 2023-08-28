package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.ScreenMode;
import flab.project.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class SettingController {

    private final SettingService settingService;

    @Operation(
            summary = "라이트/다크 모드 수정 하기 API"
    )
    @Parameters(
            value = {
                    @Parameter(name = "userId", description = "설정 상태를 확인 하고자 하는 유저의 id", required = true),
            }
    )
    @PatchMapping("/users/{userId}/settings")
    public SuccessResponse updateScreenMode(
            @PathVariable("userId") @Positive long userId,
            @RequestParam("screen-mode") ScreenMode screenMode,
            HttpServletResponse httpServletResponse
    ) {
        return settingService.updateScreenMode(userId, screenMode, httpServletResponse);
    }
}

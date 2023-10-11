package flab.project.controller;

import flab.project.common.jwt.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.FormLoginRequest;
import flab.project.data.dto.TokenDto;
import flab.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/form")
    public SuccessResponse<TokenDto> formLogin(@RequestBody FormLoginRequest formLoginRequest) {
        TokenDto tokenDto = authService.formLogin(formLoginRequest);

        return new SuccessResponse<>(tokenDto);
    }

    @PostMapping("/reissue")
    public SuccessResponse<TokenDto> reissue(
            @LoggedInUserId Long userId,
            Authentication authentication
    ) {
        TokenDto tokenDto = authService.reissue(userId,authentication);

        return new SuccessResponse<>(tokenDto);
    }

    @GetMapping("/logout/success")
    public SuccessResponse<Void> returnLogoutResponse() {
        return new SuccessResponse<>();
    }
}
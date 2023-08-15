package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.UpdateSocialAccountRequestDto;
import flab.project.facade.SocialAccountFacade;
import flab.project.service.SocialAccountService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
public class SocialAccountController {

    private final SocialAccountFacade socialAccountFacade;
    private final SocialAccountService socialAccountService;

    @PostMapping("/users/{userId}/social-accounts")
    public SuccessResponse addSocialAccount(
            @PathVariable("userId") @Positive long userId,
            @Validated @RequestBody UpdateSocialAccountRequestDto updateSocialAccount
    ) {
        return socialAccountFacade.addSocialAccount(updateSocialAccount);
    }

    @DeleteMapping("/users/{userId}/social-accounts")
    public SuccessResponse deleteSocialAccount(
            @PathVariable("userId") @Positive long userId,
            @Validated @RequestBody UpdateSocialAccountRequestDto updateSocialAccount
    ) {
        return socialAccountService.deleteSocialAccount(updateSocialAccount);
    }
}

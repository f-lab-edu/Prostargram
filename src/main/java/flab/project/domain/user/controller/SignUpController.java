package flab.project.domain.user.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.facade.InterestFacade;
import flab.project.domain.user.facade.SocialAccountFacade;
import flab.project.domain.user.model.AddInterest;
import flab.project.domain.user.model.SignUp;
import flab.project.domain.user.model.UpdateSocialAccountRequestDto;
import flab.project.domain.user.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RequiredArgsConstructor
@RestController
public class SignUpController {

    private final SignUpService signUpService;
    private final SocialAccountFacade socialAccountFacade;
    private final InterestFacade interestFacade;

    // Todo 비밀번호 필터링에 대한 커스텀 어노테이션 추가 예정
    @Operation(summary = "회원가입 API - 1단계")
    @Parameters({
            @Parameter(name = "emailToken", description = "이메일 검증 토큰", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "userNameToken", description = "닉네임 검증 토큰", in = ParameterIn.QUERY, required = true)})
    @PostMapping(value = "/users")
    public SuccessResponse addUser(@RequestBody @Valid SignUp signUp,
                                              @RequestParam @NotBlank String emailToken,
                                              @RequestParam @NotBlank String userNameToken) {
        signUpService.addUser(signUp, emailToken, userNameToken);

        return new SuccessResponse<>();
    }

    @Operation(summary = "회원가입 API - 2단계")
    @Parameters({
            @Parameter(name = "userId", description = "유저의 id", in = ParameterIn.PATH, required = true),
    })
    @PostMapping(value = "/users/{userId}/social-accounts-and-interests")
    public SuccessResponse addSocialAccountAndInterest(@PathVariable("userId") @Positive long userId,
                                                       @RequestBody @Valid @Size(max = 3) Optional<List<UpdateSocialAccountRequestDto>> socialAccounts,
                                                       @RequestBody @Valid Optional<List<String>> recommendInterests,
                                                       @RequestBody @Valid @Size(max = 10) Optional<List<AddInterest>> customInterests) {

        socialAccounts.ifPresent(socialAccountList -> {
            for (UpdateSocialAccountRequestDto socialAccount : socialAccountList) {
                socialAccount.convertEscapeCharacter();
                socialAccountFacade.addSocialAccount(socialAccount);
            }
        });

        recommendInterests.ifPresent(interestList -> {
            for (String interest : interestList) {
                AddInterest addInterest = new AddInterest(userId, interest);
                interestFacade.addInterest(addInterest);
            }
        });

        customInterests.ifPresent(interestList -> {
            for (AddInterest interest : interestList) {
                interestFacade.addInterest(interest);
            }
        });

        return new SuccessResponse<>();
    }
}

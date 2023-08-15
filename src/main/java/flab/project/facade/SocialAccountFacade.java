package flab.project.facade;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.UpdateSocialAccountRequestDto;
import flab.project.data.dto.model.SocialAccount;
import flab.project.service.IconService;
import flab.project.service.SocialAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SocialAccountFacade {
    private final SocialAccountService socialAccountService;
    private final IconService iconService;

    @Transactional
    public SuccessResponse addSocialAccount(UpdateSocialAccountRequestDto updateSocialAccount) {
        // Domain에 해당하는 Icon 찾기
        SocialAccount socialAccount = new SocialAccount(updateSocialAccount);
        iconService.setIconId(socialAccount);

        // 소셜 계정 개수 제한 정책 확인
        socialAccountService.checkNumberLimitOfSocialAccount(updateSocialAccount.getUserId());

        // 소셜 계정 추가
        socialAccountService.addSocialAccount(socialAccount);

        return new SuccessResponse();
    }
}

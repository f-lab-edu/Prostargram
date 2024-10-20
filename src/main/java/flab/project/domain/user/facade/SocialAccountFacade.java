package flab.project.domain.user.facade;

import flab.project.domain.user.model.UpdateSocialAccountRequestDto;
import flab.project.domain.user.model.SocialAccount;
import flab.project.domain.user.service.IconService;
import flab.project.domain.user.service.SocialAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SocialAccountFacade {

    private final SocialAccountService socialAccountService;
    private final IconService iconService;

    @Transactional
    public void addSocialAccount(UpdateSocialAccountRequestDto updateSocialAccount) {
        //todo 추후 feat/#26 브랜치 머지되면 BadwordCheck 로직 추가 예정.
        updateSocialAccount.convertEscapeCharacter();

        SocialAccount socialAccount = new SocialAccount(updateSocialAccount);
        iconService.setIconId(socialAccount);

        socialAccountService.checkNumberLimitOfSocialAccount(updateSocialAccount.getUserId());

        socialAccountService.addSocialAccount(socialAccount);
    }
}
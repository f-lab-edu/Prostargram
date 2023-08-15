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
        //todo 추후 feat/#26 브랜치 머지되면 BadwordCheck 로직 추가 예정.
        updateSocialAccount.convertEscapeCharacter();

        SocialAccount socialAccount = new SocialAccount(updateSocialAccount);
        iconService.setIconId(socialAccount);

        socialAccountService.checkNumberLimitOfSocialAccount(updateSocialAccount.getUserId());

        socialAccountService.addSocialAccount(socialAccount);

        return new SuccessResponse();
    }

}

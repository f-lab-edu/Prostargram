package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NumberLimitOfSocialAccountsExceededException;
import flab.project.data.dto.UpdateSocialAccountRequestDto;
import flab.project.data.dto.model.SocialAccount;
import flab.project.mapper.SocialAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static flab.project.common.Constraints.NUMBER_LIMIT_OF_SOCIAL_ACCOUNTS;

@RequiredArgsConstructor
@Service
public class SocialAccountService {

    private final SocialAccountMapper socialAccountMapper;

    public void addSocialAccount(SocialAccount socialAccount) {
        socialAccountMapper.save(socialAccount);
    }

    public void checkNumberLimitOfSocialAccount(long userId) {
        int numberOfExistingSocialAccounts = socialAccountMapper.getNumberOfExistingSocialAccounts(userId);

        if (numberOfExistingSocialAccounts >= NUMBER_LIMIT_OF_SOCIAL_ACCOUNTS) {
            throw new NumberLimitOfSocialAccountsExceededException();
        }
    }

    public SuccessResponse deleteSocialAccount(UpdateSocialAccountRequestDto updateSocialAccount) {
        SocialAccount socialAccount = new SocialAccount(updateSocialAccount);

        socialAccountMapper.remove(socialAccount);
        return new SuccessResponse();
    }
}

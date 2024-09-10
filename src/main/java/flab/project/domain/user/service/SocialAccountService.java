package flab.project.domain.user.service;

import flab.project.config.exception.NumberLimitOfSocialAccountsExceededException;
import flab.project.domain.user.exception.SocialAccountNotFoundException;
import flab.project.domain.user.model.UpdateSocialAccountRequestDto;
import flab.project.domain.user.model.SocialAccount;
import flab.project.domain.user.mapper.SocialAccountMapper;
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

    public void deleteSocialAccount(UpdateSocialAccountRequestDto updateSocialAccount) {
        SocialAccount socialAccount = new SocialAccount(updateSocialAccount);

        int numberOfDeletedRows = socialAccountMapper.remove(socialAccount);
        if (numberOfDeletedRows == 0) {
            throw new SocialAccountNotFoundException();
        }
    }
}
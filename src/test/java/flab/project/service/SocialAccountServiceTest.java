package flab.project.service;

import flab.project.config.exception.NumberLimitOfSocialAccountsExceededException;
import flab.project.domain.user.mapper.SocialAccountMapper;
import flab.project.domain.user.service.SocialAccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SocialAccountServiceTest {

    @InjectMocks
    private SocialAccountService socialAccountService;
    @Mock
    private SocialAccountMapper socialAccountMapper;

    @DisplayName("기존 소셜 계정이 2개 이하로 존재하면 NumberLimitOfSocialAccountsExceededException이 발생하지 않는다.")
    @Test
    void doesNotThrowExceptionWhenExisitingSocialAccountNumberIsLessThan3() {
        // given
        given(socialAccountMapper.getNumberOfExistingSocialAccounts(anyLong()))
                .willReturn(2);

        // when & then
        assertThatCode(() -> socialAccountService.checkNumberLimitOfSocialAccount(1))
                .doesNotThrowAnyException();
    }

    @DisplayName("기존 소셜 계정이 3개 이상 존재하면 NumberLimitOfSocialAccountsExceededException이 발생한다.")
    @Test
    void throwExceptionWhenExisitingSocialAccountNumberIsNotLessThan3() {
        // given
        given(socialAccountMapper.getNumberOfExistingSocialAccounts(anyLong()))
                .willReturn(3);

        // when & then
        assertThatThrownBy(() -> socialAccountService.checkNumberLimitOfSocialAccount(1))
                .isInstanceOf(NumberLimitOfSocialAccountsExceededException.class);
    }
}
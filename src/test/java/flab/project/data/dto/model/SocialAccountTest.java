package flab.project.data.dto.model;

import flab.project.domain.user.model.SocialAccount;
import flab.project.domain.user.model.UpdateSocialAccountRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SocialAccountTest {

    @DisplayName("socialAccountUrl에서 domain을 가져올 수 있다.")
    @Test
    void getDomain() {
        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);

        // when
        String domain = socialAccount.getDomain();

        // then
        assertThat(domain).isEqualTo("github");
    }
}
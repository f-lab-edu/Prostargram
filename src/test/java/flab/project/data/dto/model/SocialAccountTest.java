package flab.project.data.dto.model;

import flab.project.data.dto.UpdateSocialAccountRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SocialAccountTest {

    @DisplayName("socialAccountUrl에서 domain을 가져올 수 있다.")
    @Test
    void getDomain(){
        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);

        // when
        String domain = socialAccount.getDomain();

        // then
        assertThat(domain).isEqualTo("github");
    }

    @DisplayName("setIconId의 매개변수로 null이 입력되면 DEFAULT_ICON_ID로 초기화된다.")
    @Test
    void iconIdInitaizedDefault_icon_idWhenParameterIsNull(){
        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://not-famous-domain.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);

        // when
        socialAccount.setIconId(null);

        // then
        assertThat(socialAccount.getIconId()).isEqualTo(1L);
    }
}
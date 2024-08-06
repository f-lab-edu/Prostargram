package flab.project.service;

import flab.project.domain.user.model.UpdateSocialAccountRequestDto;
import flab.project.domain.user.model.SocialAccount;
import flab.project.domain.user.service.IconService;
import flab.project.domain.user.mapper.IconMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class IconServiceTest {

    @InjectMocks
    private IconService iconService;
    @Mock
    private IconMapper iconMapper;

    @DisplayName("setIconId는 매개변수로 넘어온 socialAccount객체의 iconId를 초기화한다.")
    @Test
    void setIconId() {
        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);

        given(iconMapper.findByDomain(socialAccount.getDomain()))
                .willReturn(10L);

        // when
        iconService.setIconId(socialAccount);

        // then
        assertThat(socialAccount.getIconId()).isEqualTo(10L);
    }

    @DisplayName("지정된 Domain이 아닌 경우, findByDomain은 null을 반환하고 이때 socialAccount의 iconId는 DEFAULT_ICON_ID로 초기화 된다.")
    @Test
    void setIconIdByDEFAULT_ICON_IDWhenFindByDomainReturnNull() {
        // given
        long userId = 1L;
        final long DEFAULT_ICON_ID = 1L;

        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(userId, "https://no-reserved-domain.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);

        given(iconMapper.findByDomain(socialAccount.getDomain()))
                .willReturn(null);

        // when
        iconService.setIconId(socialAccount);

        // then
        assertThat(socialAccount.getIconId()).isEqualTo(DEFAULT_ICON_ID);
    }

    @DisplayName("매개변수로 넘어온 socialAccount객체의 domain을 이용해 iconMapper의 findByDomain()메서드를 호출한다.")
    @Test
    void iconServiceCallfindByDomainMethodWithSocialAccountObjectDomainField() {
        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);

        // when
        iconService.setIconId(socialAccount);

        // then
        then(iconMapper).should().findByDomain(socialAccount.getDomain());
    }
}
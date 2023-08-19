package flab.project.service;

import flab.project.data.dto.UpdateSocialAccountRequestDto;
import flab.project.data.dto.model.SocialAccount;
import flab.project.mapper.IconMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    void setIconId(){
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);
        given(iconMapper.findByDomain(socialAccount.getDomain()))
                .willReturn(10L);

        iconService.setIconId(socialAccount);

        assertThat(socialAccount.getIconId()).isEqualTo(10L);
    }

    @DisplayName("지정된 Domain이 아닌 경우, findByDomain은 null을 반환하고 이때 socialAccount의 iconId는 DEFAULT_ICON_ID로 초기화 된다.")
    @Test
    void setIconIdByDEFAULT_ICON_IDWhenFindByDomainReturnNull(){
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);
        given(iconMapper.findByDomain(socialAccount.getDomain()))
                .willReturn(null);

        iconService.setIconId(socialAccount);

        assertThat(socialAccount.getIconId()).isEqualTo(1);
    }

    @DisplayName("매개변수로 넘어온 socialAccount객체의 domain을 이용해 iconMapper의 findByDomain()메서드를 호출한다.")
    @Test
    void iconServiceCallfindByDomainMethodWithSocialAccountObjectDomainField() {
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");
        SocialAccount socialAccount = new SocialAccount(updateSocialAccountRequestDto);

        iconService.setIconId(socialAccount);

        then(iconMapper).should().findByDomain(socialAccount.getDomain());
    }

}
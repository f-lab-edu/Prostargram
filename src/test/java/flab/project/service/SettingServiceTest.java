package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.domain.user.model.Settings;

import flab.project.domain.user.service.SettingService;
import flab.project.domain.user.mapper.SettingMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static flab.project.domain.user.enums.PublicScope.PRIVATE;
import static flab.project.domain.user.enums.PublicScope.PUBLIC;

@ExtendWith(MockitoExtension.class)
class SettingServiceTest {

    @InjectMocks
    private SettingService settingService;
    @Mock
    private SettingMapper settingMapper;

    @DisplayName("유저의 설정 상태 정보를 가져올 수 있다.")
    @Test
    void getPersonalSettings() {
        // given
        long userId = 1L;

        given(settingMapper.getPersonalSettingsByUserId(userId))
                .willReturn(new Settings());

        // when
        SuccessResponse successResponse = settingService.getPersonalSettings(userId);

        // then
        then(settingMapper).should().getPersonalSettingsByUserId(userId);

        assertThat(successResponse)
                .extracting("isSuccess", "message", "code")
                .containsExactly(SUCCESS.isSuccess(), SUCCESS.getMessage(), SUCCESS.getCode());
        assertThat(successResponse.getResult()).isInstanceOf(Settings.class);
    }

    @DisplayName("가져온 유저의 설정 상태 정보가 없다면 NotExistUserException을 던진다.")
    @Test
    void throwRuntimeExceptionIfPersonalSettingsAreNull() {
        // given
        long userId = 1L;

        given(settingMapper.getPersonalSettingsByUserId(userId))
                .willReturn(null);

        // when & then
        assertThatThrownBy(() -> settingService.getPersonalSettings(userId))
                .isInstanceOf(NotExistUserException.class);
    }

    @DisplayName("계정 공개 여부를 PUBLIC으로 수정할 수 있다.")
    @Test
    void updateUserPublicScopeToPublic() {
        // given
        long userId = 1;

        given(settingMapper.updateUserPublicScope(userId, PUBLIC))
                .willReturn(1);

        // when
        SuccessResponse successResponse = settingService.updateUserPublicScope(userId, PUBLIC);

        // then
        assertThat(successResponse)
                .extracting("isSuccess", "message", "code")
                .containsExactly(SUCCESS.isSuccess(), SUCCESS.getMessage(), SUCCESS.getCode());
    }

    @DisplayName("계정 공개 여부를 PRIVATE으로 수정할 수 있다.")
    @Test
    void updateUserPublicScopeToPrivate() {
        // given
        long userId = 1;

        given(settingMapper.updateUserPublicScope(userId, PRIVATE))
                .willReturn(1);

        // when
        SuccessResponse successResponse = settingService.updateUserPublicScope(userId, PRIVATE);

        // then
        assertThat(successResponse)
                .extracting("isSuccess", "message", "code")
                .containsExactly(SUCCESS.isSuccess(), SUCCESS.getMessage(), SUCCESS.getCode());
    }

    @DisplayName("계정 공개 여부 수정 메서드를 통해 아무런 row도 수정되지 않으면 RuntimeException을 던진다.")
    @Test
    void throwRuntimeExceptionWhenUpdateUserPublicScope() {
        // given
        long userId = 1;

        given(settingMapper.updateUserPublicScope(userId, PRIVATE))
                .willReturn(0);

        // when & then
        assertThatThrownBy(() -> settingService.updateUserPublicScope(userId, PUBLIC))
                .isInstanceOf(RuntimeException.class);
    }
}
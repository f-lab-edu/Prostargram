package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.Settings;
import flab.project.mapper.SettingMapper;
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

    @DisplayName("가져온 유저의 설정 상태 정보가 없다면 RuntimeException을 던진다.")
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
}
package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.ScreenMode;
import flab.project.service.SettingService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static flab.project.data.enums.PublicScope.PRIVATE;
import static flab.project.data.enums.PublicScope.PUBLIC;
import static flab.project.data.enums.ScreenMode.LIGHT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = SettingController.class)
class SettingControllerTest {

    private static final String UPDATE_SCREEN_MODE_URL = "/users/{userId}/settings";
    private static final String GET_PERSONAL_SETTINGS_URL = "/users/{userId}/settings";
    private static final String UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL = "/users/{userId}/settings/public-scope/public";
    private static final String UPDATE_USER_PUBLIC_SCOPE_TO_PRIVATE_URL = "/users/{userId}/settings/public-scope/private";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SettingService settingService;

    @DisplayName("유저의 스크린 모드(라이트/다크 모드)를 수정할 수 있다.")
    @Test
    void updateScreenMode() throws Exception {
        // given
        long userId = 1L;
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        given(settingService.updateScreenMode(anyLong(), any(ScreenMode.class), any(HttpServletResponse.class)))
                .willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                        patch(UPDATE_SCREEN_MODE_URL, userId)
                                .queryParam("screen-mode", LIGHT.name())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @DisplayName("유저의 스크린 모드(라이트/다크 모드)를 수정할 때, userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenUpdateScreenMode() throws Exception {
        // given
        long zeroUserId = 0L;
        long negativeUserId = -1L;
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        // when & then
        mockMvc.perform(
                        patch(UPDATE_SCREEN_MODE_URL, zeroUserId)
                                .queryParam("screen-mode", LIGHT.name())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        patch(UPDATE_SCREEN_MODE_URL, negativeUserId)
                                .queryParam("screen-mode", LIGHT.name())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("유저의 스크린 모드(라이트/다크 모드)를 수정할 때, screen-mode는 ScreenMode Enum이어야 한다.")
    @Test
    void screenModeIsInstanceOfScreenModeEnumWhenUpdateScreenMode() throws Exception {
        // given
        long userId = 1L;
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        // when & then
        mockMvc.perform(
                        patch(UPDATE_SCREEN_MODE_URL, userId)
                                .queryParam("screen-mode", "notInstanceOfScreenMode")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("유저의 설정 상태 정보를 가져올 수 있다.")
    @Test
    void getPersonalSettings() throws Exception {
        // given
        long userId = 1L;

        given(settingService.getPersonalSettings(userId))
                .willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                        get(GET_PERSONAL_SETTINGS_URL, userId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @DisplayName("유저의 설정 상태 정보를 가져올 때, userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenGetPersonalSettings() throws Exception {
        // given
        long zeroUserId = 0L;
        long negativeUserId = -1L;

        // when & then
        mockMvc.perform(
                        get(GET_PERSONAL_SETTINGS_URL, zeroUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        get(GET_PERSONAL_SETTINGS_URL, negativeUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("유저의 계정 공개 여부를 PUBLIC으로 수정할 수 있다.")
    @Test
    void updateUserPublicScopeToPublic() throws Exception {
        // given
        long userId = 1;

        given(settingService.updateUserPublicScope(userId, PUBLIC))
                .willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL, userId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        then(settingService).should().updateUserPublicScope(userId, PUBLIC);
    }

    @DisplayName("유저의 계정 공개 여부를 PUBLIC으로 수정 할 때, userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenUpdateUserPublicScope() throws Exception {
        // given
        long zeroUserId = 0;
        long negativeUserId = -1;

        // when & then
        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL, zeroUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL, negativeUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("유저의 계정 공개 여부를 PRIVATE으로 수정할 수 있다.")
    @Test
    void updateUserPublicScopeToPrivate() throws Exception {
        // given
        long userId = 1;

        given(settingService.updateUserPublicScope(userId, PRIVATE))
                .willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PRIVATE_URL, userId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        then(settingService).should().updateUserPublicScope(userId, PRIVATE);
    }

    @DisplayName("유저의 계정 공개 여부를 PRIVATE으로 수정 할 때, userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenUpdateUserPrivateScope() throws Exception {
        // given
        long zeroUserId = 0;
        long negativeUserId = -1;

        // when & then
        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PRIVATE_URL, zeroUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL, negativeUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }
}
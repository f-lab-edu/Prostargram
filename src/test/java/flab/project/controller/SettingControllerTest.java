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
import static flab.project.data.enums.ScreenMode.LIGHT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SettingController.class)
class SettingControllerTest {

    private static final String UPDATE_SCREEN_MODE_URL = "/users/{userId}/settings";

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
}
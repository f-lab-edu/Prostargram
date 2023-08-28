package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.SettingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SettingController.class)
class SettingControllerTest {

    private static final String GET_PERSONAL_SETTINGS_URL = "/users/{userId}/settings";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SettingService settingService;

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
}
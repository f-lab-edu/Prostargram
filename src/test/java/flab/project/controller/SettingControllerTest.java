package flab.project.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.PublicScope;
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
import static flab.project.data.enums.PublicScope.PRIVATE;
import static flab.project.data.enums.PublicScope.PUBLIC;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SettingController.class)
class SettingControllerTest {
    private static final String UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL = "/users/{userId}/settings/public-scope/public";
    private static final String UPDATE_USER_PUBLIC_SCOPE_TO_PRIVATE_URL = "/users/{userId}/settings/public-scope/private";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SettingService settingService;

    @DisplayName("유저의 계정 공개 여부를 PUBLIC으로 수정할 수 있다.")
    @Test
    void updateUserPublicScopeToPublic() throws Exception {
        long userId=1;

        given(settingService.updateUserPublicScope(userId, PUBLIC))
                .willReturn(new SuccessResponse());

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
        long ZeroUserId=0;
        long NegativeUserId=-1;

        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL, ZeroUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL, NegativeUserId)
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
        long userId=1;

        given(settingService.updateUserPublicScope(userId, PRIVATE))
                .willReturn(new SuccessResponse());

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
        long ZeroUserId=0;
        long NegativeUserId=-1;

        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PRIVATE_URL, ZeroUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        patch(UPDATE_USER_PUBLIC_SCOPE_TO_PUBLIC_URL, NegativeUserId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }
}
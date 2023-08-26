package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    private static final String GET_PROFILE_PAGE_INFO_URL = "/users/{userId}/profile_page";
    private static final String GET_PROFILE_UPDATE_PAGE_INFO_URL = "/users/{userId}/profile_update_page";

    @DisplayName("프로필 페이지 정보를 가져올 수 있다.")
    @Test
    void getProfilePageInfo() throws Exception {
        long userId = 1;

        given(userService.getProfileInfo(anyLong(), any(GetProfileRequestType.class)))
                .willReturn(new SuccessResponse());

        mockMvc.perform(
                        get(GET_PROFILE_PAGE_INFO_URL, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @DisplayName("프로필 페이지 정보를 가져올 때, userId는 항상 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenGetProfilePageInfo() throws Exception {
        long zeroUserId = 0;
        long negativeUserId = -1;

        given(userService.getProfileInfo(anyLong(), any(GetProfileRequestType.class)))
                .willReturn(new SuccessResponse());

        mockMvc.perform(
                        get(GET_PROFILE_PAGE_INFO_URL, negativeUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        mockMvc.perform(
                        get(GET_PROFILE_PAGE_INFO_URL, zeroUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

    }

    @DisplayName("프로필 수정 페이지 정보를 가져올 수 있다.")
    @Test
    void getUpdateProfilePageInfo() throws Exception {
        long userId = 1;

        given(userService.getProfileInfo(anyLong(), any(GetProfileRequestType.class)))
                .willReturn(new SuccessResponse());

        mockMvc.perform(
                        get(GET_PROFILE_UPDATE_PAGE_INFO_URL, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @DisplayName("프로필 수정 페이지 정보를 가져올 때, userId는 양수여야 한다..")
    @Test
    void userIdMustBePositiveWhenGetUpdateProfilePageInfo() throws Exception {
        long zeroUserId = 0;
        long negativeUserId = -1;

        given(userService.getProfileInfo(anyLong(), any(GetProfileRequestType.class)))
                .willReturn(new SuccessResponse());

        mockMvc.perform(
                        get(GET_PROFILE_UPDATE_PAGE_INFO_URL, negativeUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        mockMvc.perform(
                        get(GET_PROFILE_UPDATE_PAGE_INFO_URL, zeroUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }
}
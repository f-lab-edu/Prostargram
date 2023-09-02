package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.facade.UserFacade;
import flab.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private static final String UPDATE_PROFILE_IMG_URL = "/users/{userId}/profile-image";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserFacade userFacade;
    @MockBean
    private UserService userService;

    @DisplayName("프로필 이미지를 수정할 수 있다.")
    @Test
    void updateProfileImg() throws Exception {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile(
                "profileImage",
                "test.txt",
                "text/plain",
                "test file".getBytes()
        );

        given(userFacade.updateProfileImage(anyLong(), any(MultipartFile.class)))
                .willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                        multipart(PATCH, UPDATE_PROFILE_IMG_URL, 1)
                                .file(multipartFile)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @DisplayName("프로필 이미지를 수정할 때, userId는 양수여야 한다.")
    @Test
    void userIdIsMustPositiveWhenUpdateProfileImg() throws Exception {
        // given
        long zeroUserId = 0L;
        long negativeUserId = -1L;

        MockMultipartFile multipartFile = new MockMultipartFile(
                "profileImage",
                "test.txt",
                "text/plain",
                "test file".getBytes()
        );

        // when & then
        mockMvc.perform(
                        multipart(PATCH, UPDATE_PROFILE_IMG_URL, zeroUserId)
                                .file(multipartFile)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        multipart(PATCH, UPDATE_PROFILE_IMG_URL, negativeUserId)
                                .file(multipartFile)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }
}
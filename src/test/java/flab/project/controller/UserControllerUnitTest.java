package flab.project.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.Profile;
import flab.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class)
class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName("프로필 수정 API의 PathVariable인 userId는 양수여야 한다.")
    @Test
    public void userIdIsPositiveWhenUpdateProfile() throws Exception {
        //given
        long userId = 1;
        Profile profile = new Profile();

        when(userService.updateProfile(eq(userId), any(Profile.class)))
            .thenReturn(new SuccessResponse<>());

        //when then
        mockMvc.perform(
                patch("/users/{userId}/profile-info", userId)
                    .content(objectMapper.writeValueAsString(profile))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));

    }

    @DisplayName("프로필 수정 API의 PathVariable인 userId에 음수가 들어가면 INVALID_USER_INPUT이 리턴된다.")
    @Test
    public void userIdCannotNegativeWhenUpdateProfile() throws Exception {
        //given
        long userId = -100;
        Profile profile = new Profile();

        when(userService.updateProfile(eq(userId), any(Profile.class)))
            .thenReturn(new SuccessResponse<>());

        //when then
        mockMvc.perform(
                patch("/users/{userId}/profile-info", userId)
                    .content(objectMapper.writeValueAsString(profile))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

    }
}
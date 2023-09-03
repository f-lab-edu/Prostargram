package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private static final String UPDATE_PROFILE_INFO_URL = "/users/{userId}/profile-info";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @DisplayName("유저 프로필을 수정할 수 있다.")
    @Test
    void updateProfile() throws Exception {
        // given
        given(userService.updateProfile(anyLong(), any(UpdateProfileRequestDto.class)))
            .willReturn(new SuccessResponse());

        UpdateProfileRequestDto updateProfileRequestDto
            = new UpdateProfileRequestDto("정민욱", "카카오", "다닐 예정입니다.");

        // when & then
        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, 1)
                    .content(objectMapper.writeValueAsString(updateProfileRequestDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @DisplayName("프로필 수정에서 userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenUpdateProfile() throws Exception {
        // given
        given(userService.updateProfile(anyLong(), any(UpdateProfileRequestDto.class)))
            .willReturn(new SuccessResponse());

        long negativeUserId = -1;
        long zeroUserId = 0;
        UpdateProfileRequestDto updateProfileRequestDto
            = new UpdateProfileRequestDto("정민욱", "카카오", "다닐 예정입니다.");

        // when & then
        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, negativeUserId)
                    .content(objectMapper.writeValueAsString(updateProfileRequestDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, zeroUserId)
                    .content(objectMapper.writeValueAsString(updateProfileRequestDto))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("유저 닉네임은 최대 16글자까지 허용된다.")
    @Test
    void userNameMaxLengthIs16() throws Exception {
        // given
        given(userService.updateProfile(anyLong(), any(UpdateProfileRequestDto.class)))
            .willReturn(new SuccessResponse());

        String USER_NAME_16_LENGTH = "가나다라마바사아자차카타파하갸냐";
        UpdateProfileRequestDto updateProfileRequestDtoWith16LengthNickName
            = new UpdateProfileRequestDto(USER_NAME_16_LENGTH, "카카오", "다닐 예정입니다.");

        // when & then
        assertThat(USER_NAME_16_LENGTH.length()).isEqualTo(16);

        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, 1)
                    .content(
                        objectMapper.writeValueAsString(updateProfileRequestDtoWith16LengthNickName))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        // given
        String USER_NAME_17_LENGTH = "가나다라마바사아자차카타파하갸냐댜";
        UpdateProfileRequestDto updateProfileRequestDtoWith17LengthNickName
            = new UpdateProfileRequestDto(USER_NAME_17_LENGTH, "카카오", "다닐 예정입니다.");

        // when & then
        assertThat(USER_NAME_17_LENGTH.length()).isEqualTo(17);

        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, 1)
                    .content(
                        objectMapper.writeValueAsString(updateProfileRequestDtoWith17LengthNickName))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("유저 닉네임에는 영어/한글/숫자/_(언더바)/.(온점)을 사용할 수 있다.")
    @Test
    void OnlyAFewCharactersAreAllowedInNickName() throws Exception {
        // given
        given(userService.updateProfile(anyLong(), any(UpdateProfileRequestDto.class)))
            .willReturn(new SuccessResponse());

        String validNickName = "aB가1_.";
        UpdateProfileRequestDto updateProfileRequestDtoWithValidNickName
            = new UpdateProfileRequestDto(validNickName, "카카오", "다닐 예정입니다.");

        // when & then
        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, 1)
                    .content(objectMapper.writeValueAsString(updateProfileRequestDtoWithValidNickName))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        // given
        String invalidNickName = "aB가1_.@";
        UpdateProfileRequestDto updateProfileRequestDtoWithInvalidNickName
            = new UpdateProfileRequestDto(invalidNickName, "카카오", "다닐 예정입니다.");

        // when & then
        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, 1)
                    .content(
                        objectMapper.writeValueAsString(updateProfileRequestDtoWithInvalidNickName))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("학교/회사정보는 최대 18글자까지 허용된다.")
    @Test
    void departmentNameMaxLengthIs18() throws Exception {
        // given
        given(userService.updateProfile(anyLong(), any(UpdateProfileRequestDto.class)))
            .willReturn(new SuccessResponse());

        String DEPARTMENT_NAME_18_LENGTH = "가나다라마바사아자차카타파하갸냐댜랴";
        UpdateProfileRequestDto updateProfileRequestDtoWith18DepartmentName
            = new UpdateProfileRequestDto("정민욱", DEPARTMENT_NAME_18_LENGTH, "다닐 예정입니다.");

        // when & then
        assertThat(DEPARTMENT_NAME_18_LENGTH.length()).isEqualTo(18);

        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, 1)
                    .content(
                        objectMapper.writeValueAsString(updateProfileRequestDtoWith18DepartmentName))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        // given
        String DEPARTMENT_NAME_19_LENGTH = "가나다라마바사아자차카타파하갸냐댜랴먀";
        UpdateProfileRequestDto updateProfileRequestDtoWith19DepartmentName
            = new UpdateProfileRequestDto("정민욱", DEPARTMENT_NAME_19_LENGTH, "다닐 예정입니다.");

        // when & then
        assertThat(DEPARTMENT_NAME_19_LENGTH.length()).isEqualTo(19);

        mockMvc.perform(
                patch(UPDATE_PROFILE_INFO_URL, 1)
                    .content(
                        objectMapper.writeValueAsString(updateProfileRequestDtoWith19DepartmentName))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }
}
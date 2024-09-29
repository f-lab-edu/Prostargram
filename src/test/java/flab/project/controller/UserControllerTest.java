package flab.project.controller;

import flab.project.common.FileStorage.UploadedFileUrl;
import flab.project.domain.user.controller.UserController;
import flab.project.domain.user.facade.UserFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.domain.user.enums.GetProfileRequestType;
import flab.project.domain.user.model.UpdateProfileRequestDto;
import flab.project.domain.user.service.UserService;
import java.net.URL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private static final String GET_PROFILE_PAGE_INFO_URL = "/users/{userId}/profile_page";
    private static final String GET_PROFILE_UPDATE_PAGE_INFO_URL = "/users/{userId}/profile_update_page";
    private static final String UPDATE_PROFILE_INFO_URL = "/users/{userId}/profile-info";
    private static final String UPDATE_PROFILE_IMG_URL = "/users/{userId}/profile-image";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserFacade userFacade;
    @MockBean
    private UserService userService;

    @WithMockUser(username = "1")
    @DisplayName("프로필 페이지 정보를 가져올 수 있다.")
    @Test
    void getProfilePageInfo() throws Exception {
        // given
        long userId = 1;

        // when & then
        mockMvc.perform(
                        get(GET_PROFILE_PAGE_INFO_URL, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
        then(userService).should().getProfileInfo(anyLong(), any(GetProfileRequestType.class));
    }

    @WithMockUser(username = "1")
    @DisplayName("프로필 페이지 정보를 가져올 때, userId는 항상 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenGetProfilePageInfo() throws Exception {
        // given
        long zeroUserId = 0;
        long negativeUserId = -1;

        // when & then
        mockMvc.perform(
                        get(GET_PROFILE_PAGE_INFO_URL, zeroUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        get(GET_PROFILE_PAGE_INFO_URL, negativeUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("프로필 수정 페이지 정보를 가져올 수 있다.")
    @Test
    void getUpdateProfilePageInfo() throws Exception {
        // given
        long userId = 1;

        // when & then
        mockMvc.perform(
                        get(GET_PROFILE_UPDATE_PAGE_INFO_URL, userId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        then(userService).should().getProfileInfo(anyLong(), any(GetProfileRequestType.class));
    }

    @WithMockUser(username = "1")
    @DisplayName("프로필 수정 페이지 정보를 가져올 때, userId는 양수여야 한다..")
    @Test
    void userIdMustBePositiveWhenGetUpdateProfilePageInfo() throws Exception {
        // given
        long zeroUserId = 0;
        long negativeUserId = -1;

        // when & then
        mockMvc.perform(
                        get(GET_PROFILE_UPDATE_PAGE_INFO_URL, zeroUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        get(GET_PROFILE_UPDATE_PAGE_INFO_URL, negativeUserId)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("유저 프로필을 수정할 수 있다.")
    @Test
    void updateProfile() throws Exception {
        // given
        willDoNothing().given(userService).updateProfile(anyLong(), any(UpdateProfileRequestDto.class));

        UpdateProfileRequestDto updateProfileRequestDto
                = new UpdateProfileRequestDto("정민욱", "카카오", "다닐 예정입니다.");

        // when & then
        mockMvc.perform(
                        patch(UPDATE_PROFILE_INFO_URL, 1)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(updateProfileRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("프로필 수정에서 userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenUpdateProfile() throws Exception {
        // given
        willDoNothing().given(userService).updateProfile(anyLong(), any(UpdateProfileRequestDto.class));

        long negativeUserId = -1;
        long zeroUserId = 0;
        UpdateProfileRequestDto updateProfileRequestDto
                = new UpdateProfileRequestDto("정민욱", "카카오", "다닐 예정입니다.");

        // when & then
        mockMvc.perform(
                        patch(UPDATE_PROFILE_INFO_URL, negativeUserId)
                                .with(csrf())
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
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(updateProfileRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("유저 닉네임은 최대 16글자까지 허용된다.")
    @Test
    void userNameMaxLengthIs16() throws Exception {
        // given
        willDoNothing().given(userService).updateProfile(anyLong(), any(UpdateProfileRequestDto.class));

        String USER_NAME_16_LENGTH = "가나다라마바사아자차카타파하갸냐";
        UpdateProfileRequestDto updateProfileRequestDtoWith16LengthNickName
                = new UpdateProfileRequestDto(USER_NAME_16_LENGTH, "카카오", "다닐 예정입니다.");

        // when & then
        assertThat(USER_NAME_16_LENGTH).hasSize(16);

        mockMvc.perform(
                        patch(UPDATE_PROFILE_INFO_URL, 1)
                                .with(csrf())
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
        assertThat(USER_NAME_17_LENGTH).hasSize(17);

        mockMvc.perform(
                        patch(UPDATE_PROFILE_INFO_URL, 1)
                                .with(csrf())
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

    @WithMockUser(username = "1")
    @DisplayName("유저 닉네임에는 영어/한글/숫자/_(언더바)/.(온점)을 사용할 수 있다.")
    @Test
    void OnlyAFewCharactersAreAllowedInNickName() throws Exception {
        // given
        willDoNothing().given(userService).updateProfile(anyLong(), any(UpdateProfileRequestDto.class));

        String validNickName = "aB가1_.";
        UpdateProfileRequestDto updateProfileRequestDtoWithValidNickName
                = new UpdateProfileRequestDto(validNickName, "카카오", "다닐 예정입니다.");

        // when & then
        mockMvc.perform(
                        patch(UPDATE_PROFILE_INFO_URL, 1)
                                .with(csrf())
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
                                .with(csrf())
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

    @WithMockUser(username = "1")
    @DisplayName("학교/회사정보는 최대 18글자까지 허용된다.")
    @Test
    void departmentNameMaxLengthIs18() throws Exception {
        // given
        willDoNothing().given(userService).updateProfile(anyLong(), any(UpdateProfileRequestDto.class));

        String DEPARTMENT_NAME_18_LENGTH = "가나다라마바사아자차카타파하갸냐댜랴";
        UpdateProfileRequestDto updateProfileRequestDtoWith18DepartmentName
                = new UpdateProfileRequestDto("정민욱", DEPARTMENT_NAME_18_LENGTH, "다닐 예정입니다.");

        // when & then
        assertThat(DEPARTMENT_NAME_18_LENGTH).hasSize(18);

        mockMvc.perform(
                        patch(UPDATE_PROFILE_INFO_URL, 1)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(updateProfileRequestDtoWith18DepartmentName))
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
        assertThat(DEPARTMENT_NAME_19_LENGTH).hasSize(19);

        mockMvc.perform(
                        patch(UPDATE_PROFILE_INFO_URL, 1)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(updateProfileRequestDtoWith19DepartmentName))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("프로필 이미지를 수정할 수 있다.")
    @Test
    void updateProfileImg() throws Exception {
        long userId = 1L;
        URL url = new URL("http", "host.domain", "file/path");
        UploadedFileUrl uploadedFileUrl = new UploadedFileUrl(url);
        given(userFacade.updateProfileImage(userId)).willReturn(uploadedFileUrl);

        // when & then
        mockMvc.perform(
                        patch(UPDATE_PROFILE_IMG_URL, userId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }
}
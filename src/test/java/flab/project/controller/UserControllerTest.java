package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.BasicPostWithUser;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.data.dto.model.BasicPost;
import flab.project.data.dto.model.BasicUser;
import flab.project.data.enums.requestparam.GetProfileRequestType;
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

import java.util.Arrays;
import java.util.List;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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


    @DisplayName("프로필 페이지 정보를 가져올 수 있다.")
    @Test
    void getProfilePageInfo() throws Exception {
        // given
        long userId = 1;

        given(userService.getProfileInfo(anyLong(), any(GetProfileRequestType.class)))
                .willReturn(new SuccessResponse());

        // when & then
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

    @DisplayName("프로필 수정 페이지 정보를 가져올 수 있다.")
    @Test
    void getUpdateProfilePageInfo() throws Exception {
        // given
        long userId = 1;

        given(userService.getProfileInfo(anyLong(), any(GetProfileRequestType.class)))
                .willReturn(new SuccessResponse());

        // when & then
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

    @DisplayName("프로필 피드 API를 처음 요청하는 경우, lastProfilePostId = null로 요청한다.")
    @Test
    void getProfileFeed_lastProfilePostIdIsNull() throws Exception {
        // given
        long userId = 1L;
        Long lastProfilePostId = null;
        long limit = 10L;

        BasicPost basicPost = new BasicPost(Arrays.asList("https://pask2202.url"));
        BasicUser basicUser = new BasicUser(1L, "이은비", "https://pask2202.url");

        BasicPostWithUser basicPostWithUser = new BasicPostWithUser(basicPost, basicUser);
        List<BasicPostWithUser> feed = Arrays.asList(basicPostWithUser);

        given(userService.getProfileFeed(userId, lastProfilePostId, limit)).willReturn(feed);

        // when & then
        mockMvc.perform(get("/users/{userId}/profile", userId)
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @DisplayName("프로필 피드를 가져올 수 있다.")
    @Test
    void getProfileFeed() throws Exception {
        // given
        long userId = 1L;
        Long lastProfilePostId = 11L;
        long limit = 10L;

        BasicPost basicPost = new BasicPost(Arrays.asList("https://pask2202.url"));
        BasicUser basicUser = new BasicUser(1L, "이은비", "https://pask2202.url");

        BasicPostWithUser basicPostWithUser = new BasicPostWithUser(basicPost, basicUser);
        List<BasicPostWithUser> feed = Arrays.asList(basicPostWithUser);

        given(userService.getProfileFeed(userId, lastProfilePostId, limit)).willReturn(feed);

        // when & then
        mockMvc.perform(get("/users/{userId}/profile", userId)
                        .param("lastProfilePostId", String.valueOf(lastProfilePostId))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @DisplayName("프로필 피드를 가져올 때, userId가 양수가 아니라면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getProfileFeed_invalidUserId() throws Exception {
        // given
        long invalidUserId = -1L;
        Long lastProfilePostId = null;
        long limit = 10L;

        // when & then
        mockMvc.perform(get("/users/{userId}/profile", invalidUserId)
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("프로필 피드를 가져올 때, lastProfilePostId가 양수가 아닐 경우 InvalidUserInput 예외를 반환한다.")
    @Test
    void getProfileFeed_invalidLastProfilePostId() throws Exception {
        // given
        long userId = 1L;
        Long invalidLastProfilePostId = -1L;
        long limit = 10L;

        // when & then
        mockMvc.perform(get("/users/{userId}/profile", userId)
                        .param("lastProfilePostId", String.valueOf(invalidLastProfilePostId))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("프로필 피드를 가져올 때, limit의 값이 양수가 아니라면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getProfileFeed_negativeLimit() throws Exception {
        // given
        long userId = 1L;
        Long lastProfilePostId = null;
        long negativeLimit = -1L;

        mockMvc.perform(get("/users/{userId}/profile", userId)
                        .param("limit", String.valueOf(negativeLimit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("프로필 피드를 가져올 때, limit의 값이 10을 초과한다면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getProfileFeed_excessLimit() throws Exception {
        // given
        long userId = 1L;
        Long lastProfilePostId = null;
        long excessLimit = 11L;

        mockMvc.perform(get("/users/{userId}/profile", userId)
                        .param("limit", String.valueOf(excessLimit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }
}
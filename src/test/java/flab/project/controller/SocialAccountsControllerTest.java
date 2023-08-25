package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.UpdateSocialAccountRequestDto;
import flab.project.facade.SocialAccountFacade;
import flab.project.service.SocialAccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SocialAccountController.class)
class SocialAccountsControllerTest {

    private static final String UPDATE_SOCIAL_ACCOUNT_API_URL = "/users/{userId}/social-accounts";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SocialAccountFacade socialAccountFacade;
    @MockBean
    private SocialAccountService socialAccountService;

    @DisplayName("소셜 계정을 추가할 수 있다.")
    @Test
    void addSocialAccount() throws Exception {
        // given
        given(socialAccountFacade.addSocialAccount(any(UpdateSocialAccountRequestDto.class)))
                .willReturn(new SuccessResponse());

        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");

        // when & then
        mockMvc.perform(
                        post(UPDATE_SOCIAL_ACCOUNT_API_URL, 1)
                                .content(objectMapper.writeValueAsString(updateSocialAccountRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @DisplayName("소셜 계정 추가 API에서 userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenAddSocialAccount() throws Exception {
        // given
        given(socialAccountFacade.addSocialAccount(any(UpdateSocialAccountRequestDto.class)))
                .willReturn(new SuccessResponse());

        UpdateSocialAccountRequestDto updateSocialAccountRequestDto1 = new UpdateSocialAccountRequestDto(-1L, "https://github.com");

        // when & then
        mockMvc.perform(
                        post(UPDATE_SOCIAL_ACCOUNT_API_URL, -1)
                                .content(objectMapper.writeValueAsString(updateSocialAccountRequestDto1))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto2 = new UpdateSocialAccountRequestDto(0, "https://github.com");

        // when & then
        mockMvc.perform(
                        post(UPDATE_SOCIAL_ACCOUNT_API_URL, 0)
                                .content(objectMapper.writeValueAsString(updateSocialAccountRequestDto2))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("소셜 계정을 삭제할 수 있다.")
    @Test
    void deleteSocialAccount() throws Exception {
        // given
        given(socialAccountService.deleteSocialAccount(any(UpdateSocialAccountRequestDto.class)))
                .willReturn(new SuccessResponse());

        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");

        // when & then
        mockMvc.perform(
                        delete(UPDATE_SOCIAL_ACCOUNT_API_URL, 1)
                                .content(objectMapper.writeValueAsString(updateSocialAccountRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));
    }

    @DisplayName("소셜 계정 삭제 API에서 userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenDeleteSocialAccount() throws Exception {
        //given
        given(socialAccountService.deleteSocialAccount(any(UpdateSocialAccountRequestDto.class)))
                .willReturn(new SuccessResponse());

        UpdateSocialAccountRequestDto updateSocialAccountRequestDto1 = new UpdateSocialAccountRequestDto(-1L, "https://github.com");

        // when & then
        mockMvc.perform(
                        delete(UPDATE_SOCIAL_ACCOUNT_API_URL, -1)
                                .content(objectMapper.writeValueAsString(updateSocialAccountRequestDto1))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto2 = new UpdateSocialAccountRequestDto(0, "https://github.com");

        // when & then
        mockMvc.perform(
                        delete(UPDATE_SOCIAL_ACCOUNT_API_URL, 0)
                                .content(objectMapper.writeValueAsString(updateSocialAccountRequestDto2))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }
}
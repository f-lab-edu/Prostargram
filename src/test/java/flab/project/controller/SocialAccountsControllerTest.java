package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.domain.user.controller.SocialAccountController;
import flab.project.domain.user.model.UpdateSocialAccountRequestDto;
import flab.project.domain.user.facade.SocialAccountFacade;
import flab.project.domain.user.service.SocialAccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SocialAccountController.class)
class SocialAccountsControllerTest {

    private static final String UPDATE_SOCIAL_ACCOUNT_API_URL = "/social-accounts";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SocialAccountFacade socialAccountFacade;
    @MockBean
    private SocialAccountService socialAccountService;

    @WithMockUser(username = "1")
    @DisplayName("소셜 계정을 추가할 수 있다.")
    @Test
    void addSocialAccount() throws Exception {
        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");

        // when & then
        mockMvc.perform(
                        post(UPDATE_SOCIAL_ACCOUNT_API_URL, 1)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(updateSocialAccountRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        then(socialAccountFacade).should().addSocialAccount(any(UpdateSocialAccountRequestDto.class));
    }

    @WithMockUser(username = "1")
    @DisplayName("소셜 계정을 삭제할 수 있다.")
    @Test
    void deleteSocialAccount() throws Exception {
        // given
        UpdateSocialAccountRequestDto updateSocialAccountRequestDto = new UpdateSocialAccountRequestDto(1L, "https://github.com");

        // when & then
        mockMvc.perform(
                        delete(UPDATE_SOCIAL_ACCOUNT_API_URL, 1)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(updateSocialAccountRequestDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        then(socialAccountService).should().deleteSocialAccount(any(UpdateSocialAccountRequestDto.class));
    }
}
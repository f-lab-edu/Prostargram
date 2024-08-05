package flab.project.controller;

import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.like.controller.PostLikeController;
import flab.project.domain.like.service.PostLikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.TestConstant.MOCK_USERID;
import static flab.project.TestConstant.MOCK_USERNAME;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostLikeController.class)
class PostLikeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostLikeService postLikeService;

    @WithMockUser(username = MOCK_USERNAME)
    @DisplayName("게시물에 좋아요를 할 수 있다.")
    @Test
    void parameterOfPostLikeIsPositive() throws Exception {
        // given
        long postId = 1L;

        given(postLikeService.addPostLike(postId, MOCK_USERID)).willReturn(new SuccessResponse<>());

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/likes", postId)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @WithMockUser(username = MOCK_USERNAME)
    @DisplayName("게시물에 좋아요를 추가할 때, postId는 양수여야 한다.")
    @Test
    void parameterOfPostLikeIsNonPositive() throws Exception {
        // given
        long negativePostId = -1L;

        // when & then
        mockMvc.perform(post("/posts/{postId}/likes", negativePostId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        // given
        long zeroPostId = 0;

        // when & then
        mockMvc.perform(post("/posts/{postId}/likes", zeroPostId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }
}
package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CommentService commentService;

    @DisplayName("댓글을 작성할 수 있다.")
    @Test
    void addComment() throws Exception {
        // given
        long postId = 1L;
        long userId = 1L;
        String content = "안녕하세요.";

        given(commentService.addComment(postId, userId, content)).willReturn(new SuccessResponse<>());

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/comment", postId)
                                .param("userId", String.valueOf(userId))
                                .param("content", content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @DisplayName("댓글을 작성하려고 할 때, postId가 양수가 아닐 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addComment_invalidPostId() throws Exception {
        // given
        long negativePostId = -1L;
        long userId = 1L;
        String content = "안녕하세요.";

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/comment", negativePostId)
                                .param("userId", String.valueOf(userId))
                                .param("content", content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("댓글을 작성하려고 할 때, userId가 양수가 아닐 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addComment_invalidUserId() throws Exception {
        // given
        long postId = 1L;
        long negativeUserId = -1L;
        String content = "안녕하세요.";

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/comment", postId)
                        .param("userId", String.valueOf(negativeUserId))
                        .param("content", content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }
}

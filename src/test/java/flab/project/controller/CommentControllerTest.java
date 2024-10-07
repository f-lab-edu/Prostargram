package flab.project.controller;

import flab.project.config.baseresponse.ResponseEnum;
import flab.project.domain.comment.model.Comment;
import flab.project.domain.comment.controller.CommentController;
import flab.project.domain.comment.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;

    @WithMockUser(username = "1")
    @DisplayName("댓글을 작성할 수 있다.")
    @Test
    void addComment_isComment() throws Exception {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = null;
        String content = "안녕하세요.";

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        given(commentService.addComment(postId, userId, parentId, content)).willReturn(comment);

        mockMvc.perform(
                        post("/posts/{postId}/comments", postId)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())) // CSRF 토큰 추가
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("대댓글을 작성할 수 있다.")
    @Test
    void addComment_isReply() throws Exception {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        given(commentService.addComment(postId, userId, parentId, content)).willReturn(comment);

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/comments", postId)
                                .param("parentId", String.valueOf(parentId))
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("대댓글을 작성할 때, postId가 양수가 아닐 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addReply_invalidPostId() throws Exception {
        // given
        long negativePostId = -1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/comments", negativePostId)
                                .param("parentId", String.valueOf(parentId))
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("대댓글을 작성할 때, parentId가 양수가 아닐 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addComment_invalidParentId() throws Exception {
        // given
        long postId = 1L;
        Long parentId = -1L;
        String content = "안녕하세요.";

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/comments", postId)
                                .param("parentId", String.valueOf(parentId))
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser(username = "1")
    @DisplayName("댓글을 작성할 때, content가 공백일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addComment_nonNullableContent() throws Exception {
        // given
        long postId = 1L;
        Long parentId = null;
        String content = " ";

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/comments", postId)
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }
}
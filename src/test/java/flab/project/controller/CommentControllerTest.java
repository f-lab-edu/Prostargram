package flab.project.controller;

import flab.project.config.baseresponse.ResponseEnum;
import flab.project.data.dto.CommentWithUser;
import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Comment;
import flab.project.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        // when & then
        mockMvc.perform(post("/posts/{postId}/comments", postId)
                        .param("userId", String.valueOf(userId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

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
        mockMvc.perform(post("/posts/{postId}/comments", postId)
                                .param("userId", String.valueOf(userId))
                                .param("parentId", String.valueOf(parentId))
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @DisplayName("대댓글을 작성할 때, postId가 양수가 아닐 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addReply_invalidPostId() throws Exception {
        // given
        long negativePostId = -1L;
        long userId = 1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        // when & then
        mockMvc.perform(post("/posts/{postId}/comments", negativePostId)
                        .param("userId", String.valueOf(userId))
                        .param("parentId", String.valueOf(parentId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("대댓글을 작성할 때, userId가 양수가 아닐 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addReply_invalidUserId() throws Exception {
        // given
        long postId = 1L;
        long negativeUserId = -1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        // when & then
        mockMvc.perform(post("/posts/{postId}/comments", postId)
                        .param("userId", String.valueOf(negativeUserId))
                        .param("parentId", String.valueOf(parentId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("대댓글을 작성할 때, parentId가 양수가 아닐 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addComment_invalidParentId() throws Exception {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = -1L;
        String content = "안녕하세요.";

        // when & then
        mockMvc.perform(post("/posts/{postId}/comments", postId)
                        .param("userId", String.valueOf(userId))
                        .param("parentId", String.valueOf(parentId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("댓글을 작성할 때, content가 공백일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addComment_nonNullableContent() throws Exception {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = null;
        String content = " ";

        // when & then
        mockMvc.perform(post("/posts/{postId}/comments", postId)
                        .param("userId", String.valueOf(userId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    // Todo (2023.12.09) return 검증 searching 중입니다.
    @DisplayName("lastCommentId가 없을 경우, 즉 처음에 댓글을 가져올 수 있다.")
    @Test
    void getComments_lastCommentIdIsNull() throws Exception {
        // given
        long postId = 1L;
        Long lastCommentId = null;
        long limit = 10L;

        // Todo @Builder를 활용할 수 있는 클래스이므로 해당 방식(= Builder)을 통해 객체를 생성 및 초기화를 해야할까?
        // Todo Comment 뿐만 아니라 BasicUser 객체 생성도 해야해서 가독성이 좀 떨어지는 것 같은데..
        Comment comment = new Comment(1L, 1L, null, "안녕하세요");
        BasicUser basicUser = new BasicUser(1L, "이은비", "https://pask2202.url");

        CommentWithUser commentWithUser = new CommentWithUser(comment, basicUser);
        List<CommentWithUser> comments = Arrays.asList(commentWithUser);

        given(commentService.getComments(postId, lastCommentId, limit)).willReturn(comments);

        // when & then
        mockMvc.perform(get("/posts/{postId}/comments", postId)
                                .param("limit", String.valueOf(limit))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @DisplayName("lastCommentId가 있을 경우, 즉 댓글을 가져올 수 있다.")
    @Test
    void getComments() throws Exception {
        // given
        long postId = 1L;
        Long lastCommentId = 11L;
        long limit = 10L;

        Comment comment = new Comment(1L, 1L, null, "안녕하세요");
        BasicUser basicUser = new BasicUser(1L, "이은비", "https://pask2202.url");

        CommentWithUser commentWithUser = new CommentWithUser(comment, basicUser);
        List<CommentWithUser> comments = Arrays.asList(commentWithUser);

        given(commentService.getComments(postId, lastCommentId, limit)).willReturn(comments);

        // when & then
        mockMvc.perform(get("/posts/{postId}/comments", postId)
                        .param("lastCommentId", String.valueOf(lastCommentId))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @DisplayName("댓글을 가져올 때, postId가 양수가 아닐 경우 InvalidUserInput 예외를 반환한다.")
    @Test
    void getComments_invalidPostId() throws Exception {
        // given
        long invalidPostId = -1L;
        Long lastCommentId = null;
        long limit = 10L;

        // when & then
        mockMvc.perform(get("/posts/{postId}/comments", invalidPostId)
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("댓글을 가져올 때, lastCommentId가 양수가 아닐 경우 InvalidUserInput 예외를 반환한다.")
    @Test
    void getComments_negativeLastCommentId() throws Exception {
        // given
        long postId = 1L;
        Long invalidLastCommentId = -1L;
        long limit = 10L;

        // when & then
        mockMvc.perform(get("/posts/{postId}/comments", postId)
                        .param("lastCommentId", String.valueOf(invalidLastCommentId))
                        .param("limit", String.valueOf(limit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("댓글을 가져올 때, limit가 양수가 아닐 경우 InvalidUserInput 예외를 반환한다.")
    @Test
    void getComments_negativeLimit() throws Exception {
        // given
        long postId = 1L;
        Long lastCommentId = null;
        long negativeLimit = -1L;

        // when & then
        mockMvc.perform(get("/posts/{postId}/comments", postId)
                        .param("limit", String.valueOf(negativeLimit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("댓글을 가져올 때, limit가 10을 초과할 경우 InvalidUserInput 예외를 반환한다.")
    @Test
    void getComments_excessLimit() throws Exception {
        // given
        long postId = 1L;
        Long lastCommentId = null;
        long excessLimit = 11L;

        // when & then
        mockMvc.perform(get("/posts/{postId}/comments", postId)
                        .param("limit", String.valueOf(excessLimit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }
}
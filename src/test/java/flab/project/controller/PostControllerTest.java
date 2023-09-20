package flab.project.controller;

import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.DeletedPostException;
import flab.project.data.dto.PostWithUser;
import flab.project.data.dto.model.BasicPost;
import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.DebatePost;
import flab.project.data.dto.model.PollPost;
import flab.project.data.enums.PostType;
import flab.project.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static flab.project.config.baseresponse.ResponseEnum.*;
import static flab.project.data.enums.PostType.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    private static final String GET_BASIC_POST_DETAIL_REQUEST_URL = "/posts/{postId}/basic-post";
    private static final String GET_DEBATE_POST_DETAIL_REQUEST_URL = "/posts/{postId}/debate-post";
    private static final String GET_POLL_POST_DETAIL_REQUEST_URL = "/posts/{postId}/poll-post";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;

    @DisplayName("일반 게시물 상세 보기를 할 수 있다.")
    @Test
    void getBasicPostDetail() throws Exception {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postService.getPostDetail(postId, userId, BASIC))
                .willReturn(new SuccessResponse(new PostWithUser(new BasicPost(), new BasicUser())));

        validateGetPostDetail(postId, GET_BASIC_POST_DETAIL_REQUEST_URL, status().isOk(), SUCCESS);
    }

    @DisplayName("토론 게시물 상세 보기를 할 수 있다.")
    @Test
    void getDebatePostDetail() throws Exception {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postService.getPostDetail(postId, userId, DEBATE))
                .willReturn(new SuccessResponse(new PostWithUser(new DebatePost(), new BasicUser())));

        validateGetPostDetail(postId, GET_DEBATE_POST_DETAIL_REQUEST_URL, status().isOk(), SUCCESS);
    }

    @DisplayName("통계 게시물 상세 보기를 할 수 있다.")
    @Test
    void getPollPostDetail() throws Exception {
        // given
        long postId = 1L;
        long userId = 1L;

        given(postService.getPostDetail(postId, userId, PostType.POLL))
                .willReturn(new SuccessResponse(new PostWithUser(new PollPost(), new BasicUser())));

        validateGetPostDetail(postId, GET_POLL_POST_DETAIL_REQUEST_URL, status().isOk(), SUCCESS);
    }

    @DisplayName("일반 게시물 상세보기를 할 때, postId는 양수여야 한다.")
    @Test
    void getBasicPostDetail_invalidPostId() throws Exception {
        // given
        long negativePostId = -1L;

        validateGetPostDetail(negativePostId, GET_BASIC_POST_DETAIL_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("토론 게시물 상세보기를 할 때, postId는 양수여야 한다.")
    @Test
    void getDebatePostDetail_invalidPostId() throws Exception {
        // given
        long negativePostId = -1L;

        validateGetPostDetail(negativePostId, GET_DEBATE_POST_DETAIL_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물 상세보기를 할 때, postId는 양수여야 한다.")
    @Test
    void getPollPostDetail_invalidPostId() throws Exception {
        // given
        long negativePostId = -1L;

        validateGetPostDetail(negativePostId, GET_POLL_POST_DETAIL_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("없는 일반 게시물을 요청했다면 DELETED_POST를 반환한다.")
    @Test
    void getBasicPostDetail_deletedPost() throws Exception {
        // given
        long deletedPostId = 99L;
        long userId = 1L;

        given(postService.getPostDetail(deletedPostId, userId, BASIC))
                .willThrow(DeletedPostException.class);

        validateGetPostDetail(deletedPostId, GET_BASIC_POST_DETAIL_REQUEST_URL, status().isNotFound(), DELETED_POST);
    }

    @DisplayName("없는 토론 게시물을 요청했다면 DELETED_POST를 반환한다.")
    @Test
    void getDebatePostDetail_deletedPost() throws Exception {
        // given
        long deletedPostId = 99L;
        long userId = 1L;

        given(postService.getPostDetail(deletedPostId, userId, DEBATE))
                .willThrow(DeletedPostException.class);

        validateGetPostDetail(deletedPostId, GET_DEBATE_POST_DETAIL_REQUEST_URL, status().isNotFound(), DELETED_POST);
    }

    @DisplayName("없는 통계 게시물을 요청했다면 DELETED_POST를 반환한다.")
    @Test
    void getPollPostDetail_deletedPost() throws Exception {
        // given
        long deletedPostId = 99L;
        long userId = 1L;

        given(postService.getPostDetail(deletedPostId, userId, POLL))
                .willThrow(DeletedPostException.class);

        validateGetPostDetail(deletedPostId, GET_POLL_POST_DETAIL_REQUEST_URL, status().isNotFound(), DELETED_POST);
    }

    private void validateGetPostDetail(long postId, String requestUrl, ResultMatcher statusCode, ResponseEnum responseEnum) throws Exception {
        requestGetBasicPostDetail(postId, requestUrl)
                .andExpect(statusCode)
                .andExpect(jsonPath("$.isSuccess").value(responseEnum.isSuccess()))
                .andExpect(jsonPath("$.code").value(responseEnum.getCode()))
                .andExpect(jsonPath("$.message").value(responseEnum.getMessage()));
    }

    private ResultActions requestGetBasicPostDetail(long postId, String requestUrl) throws Exception {
        return mockMvc.perform(
                        get(requestUrl, postId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
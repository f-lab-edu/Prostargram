package flab.project.controller;

import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotFoundException;
import flab.project.domain.post.enums.PostType;
import flab.project.domain.post.controller.VoteController;
import flab.project.domain.post.service.VoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoteController.class)
class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VoteService voteService;

    @WithMockUser
    @DisplayName("토론 게시물에 투표할 수 있다.")
    @Test
    void addDebatePostVote() throws Exception {
        // given
        long postId = 1L;
        long optionId = 1L;
        long userId = 3L;

        given(voteService.addPostVote(postId, Set.of(optionId), userId, PostType.DEBATE)).willReturn(new SuccessResponse<>());

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/debate", postId)
                        .with(csrf())
                        .param("optionId", String.valueOf(optionId))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @WithMockUser
    @DisplayName("통계 게시물에 투표할 수 있다.")
    @Test
    void addPollPostVote() throws Exception {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 3L;

        given(voteService.addPostVote(postId, optionIds, userId, PostType.POLL)).willReturn(new SuccessResponse<>());

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/poll", postId)
                        .with(csrf())
                        .param("optionIds", optionIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @WithMockUser
    @DisplayName("토론 게시물에 투표할 때, postId가 음수일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addDebatePostVote_invalidPostId() throws Exception {
        // given
        long negativePostId = -1L;
        long optionId = 1L;
        long userId = 1L;

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/debate", negativePostId)
                        .with(csrf())
                        .param("optionId", String.valueOf(optionId))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser
    @DisplayName("토론 게시물에 투표할 때, userId가 음수일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addDebatePostVote_invalidUserId() throws Exception {
        // given
        long postId = 1L;
        long optionId = 1L;
        long negativeUserId = -1L;

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/debate", postId)
                        .with(csrf())
                        .param("optionId", String.valueOf(optionId))
                        .param("userId", String.valueOf(negativeUserId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser
    @DisplayName("통계 게시물에 투표할 때, postId가 0일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addPollPostVote_invalidPostId() throws Exception {
        // given
        long zeroPostId = 0L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 1L;

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/poll", zeroPostId)
                        .with(csrf())
                        .param("optionIds", optionIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser
    @DisplayName("통계 게시물에 투표할 때, userId가 0일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addPollPostVote_invalidUserId() throws Exception {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long zeroUserId = 0L;

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/poll", postId)
                        .with(csrf())
                        .param("optionIds", optionIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .param("userId", String.valueOf(zeroUserId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser
    @DisplayName("토론 게시물에 투표할 때, optionId가 음수 또는 0일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addDebatePostVote_invalidOptionId() throws Exception {
        // given
        long postId = 1L;
        long negativeOptionId = -1L;
        long userId = 1L;

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/debate", postId)
                        .with(csrf())
                        .param("optionId", String.valueOf(negativeOptionId))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        // given
        long zeroOptionId = 0L;

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/debate", postId)
                        .with(csrf())
                        .param("optionId", String.valueOf(zeroOptionId))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser
    @DisplayName("통계 게시물에 투표할 때, optionId가 음수 또는 0일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addPollPostVote_invalidOptionIds() throws Exception {
        // given
        long postId = 1L;
        Set<Long> negativeOptionIds = Set.of(-1L, 0L, 1L);
        long userId = 1L;

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/poll", postId)
                        .with(csrf())
                        .param("optionIds", negativeOptionIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @WithMockUser
    @DisplayName("존재하지 않는 토론 게시물이라면, NotFoundException을 반환한다.")
    @Test
    void addDebatePostVote_invalidDebatePost() throws Exception {
        // given
        long deletedPostId = 99L;
        long optionId = 1L;
        long userId = 1L;

        given(voteService.addPostVote(deletedPostId, Set.of(1L), userId, PostType.DEBATE))
            .willThrow(NotFoundException.class);

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/votes/debate", deletedPostId)
                                .with(csrf())
                                .param("optionId", String.valueOf(optionId))
                                .param("userId", String.valueOf(userId))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @WithMockUser
    @DisplayName("존재하지 않는 통계 게시물이라면, NotFoundException을 반환한다.")
    @Test
    void addPollPostVote_invalidPollPost() throws Exception {
        // given
        long deletedPostId = 99L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 1L;

        given(voteService.addPostVote(deletedPostId, optionIds, userId, PostType.POLL)).willThrow(NotFoundException.class);

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/votes/poll", deletedPostId)
                                .with(csrf())
                                .param("optionIds", optionIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                                .param("userId", String.valueOf(userId))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }
}
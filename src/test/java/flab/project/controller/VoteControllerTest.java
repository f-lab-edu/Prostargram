package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.VoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoteController.class)
public class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private VoteService voteService;

    @DisplayName("토론 게시물에 투표할 수 있다.")
    @Test
    void addDebatePostVote() throws Exception {
        // given
        long postId = 1L;
        long optionId = 2L;
        long userId = 3L;

        given(voteService.addDebatePostVote(postId, optionId, userId)).willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/debate", postId)
                        .param("optionId", String.valueOf(optionId))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @DisplayName("통계 게시물에 투표할 수 있다.")
    @Test
    void addPollPostVote() throws Exception {
        // given
        long postId = 1L;
        List<Long> optionIds = List.of(1L, 2L);
        long userId = 3L;

        given(voteService.addPollPostVote(postId, optionIds, userId)).willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/votes/poll", postId)
                        .param("optionIds", optionIds.stream().map(String::valueOf).collect(Collectors.joining(",")))
                        .param("userId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }
}
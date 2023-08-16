package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.PostType;
import flab.project.service.VoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @Mock
    private VoteService voteService;

    @DisplayName("토론 게시물에 투표를 할 때, 유저는 오로지 하나의 선택지만 고를 수 있다.")
    @Test
    void onlySelectOneOption() throws Exception {
        // given
        PostType postType = PostType.DEBATE;
        List<Long> optionIds = List.of(1L);
        long userId = 1L;

        given(voteService.addPostVote(postType, optionIds, userId)).willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                        post("/posts/{postId}/votes", "1")
                                .param("postType", String.valueOf("postType"))
                                .param("optionId", String.valueOf("optionId"))
                                .param("userId", String.valueOf("APPLICATION_JSON"))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }
}

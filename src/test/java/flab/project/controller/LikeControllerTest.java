package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.service.LikeService;
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

@WebMvcTest(controllers = LikeController.class)
public class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LikeService likeService;

    @DisplayName("게시물에 좋아요를 추가할 때, postId 및 userId는 양수 값만 가능하다.")
    @Test
    void parameterOfLikeIsPostitive() throws Exception {
        // given
        long postId1 = 1L;
        long userId1 = 2L;

        given(likeService.addPostLike(postId1, userId1)).willReturn(new SuccessResponse());

        // when & then
        mockMvc.perform(
                post("/posts/{postId}/likes", postId1)
                        .param("userId", String.valueOf(userId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }

    @DisplayName("게시물에 좋아요를 추가할 때, postId 및 userId는 음수 값일 수 없다.")
    @Test
    void parameterOfLikeIsNonPositive() throws Exception {
        // given
        long postId2 = -1L;
        long userId2 = -2L;

        /*
        * 양수가 아닌 음수일 경우, 올바른 예외를 던지는지 확인하고 싶어서 작성한 코드입니다.
        * 그런데, "왜 postId 또는 userId는 0보다 커야 한다." + "code : 400을 기대했지만 500이 반환된다."라는 오류가 뜨는지 모르겠습니다.
        */

        given(likeService.addPostLike(postId2, userId2)).willThrow(new InvalidUserInputException("postId 또는 userId는 0보다 커야 합니다."));

        // when & then
        mockMvc.perform(post("/posts/{postId}/likes", postId2)
                        .param("userId", String.valueOf(userId2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }
}

package flab.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.Follows;
import flab.project.data.dto.model.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.service.FollowService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FollowController.class)
class FollowControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FollowService followService;

    private static final String GET_FOLLOWERS_REQUEST_URL = "/users/{userId}/followers";
    private static final String GET_FOLLOWINGS_REQUEST_URL = "/users/{userId}/followings";
    private static final String GET_FOLLOWERS_AND_FOLLOWINGS_REQUEST_URL = "/users/{userId}/follows/all";
    private static final String ADD_FOLLOWS_REQUEST_URL = "/users/{userId}/follows";
    private static final String DELETE_FOLLOWS_REQUEST_URL = "/users/{userId}/follows";
    @DisplayName("팔로워를 가져올 때, userId는 양수여야 한다.")
    @Test
    void getFollowersWithNonPositiveId() throws Exception {
        mockMvc.perform(
                get(GET_FOLLOWERS_REQUEST_URL,-1)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                get(GET_FOLLOWERS_REQUEST_URL,0)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워를 가져올 때, userId는 정수가 보내줘야 하며 타입 변환이 불가능할 경우 INVALID_USER_INPUT을 반환한다.")
    @Test
    void getFollowersWithNotLongId() throws Exception {
        mockMvc.perform(
                get(GET_FOLLOWERS_REQUEST_URL,"a")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로잉을 가져올 때, userId는 양수여야 한다.")
    @Test
    void getFollowingsWithNonPositiveId() throws Exception {
        mockMvc.perform(
                get(GET_FOLLOWINGS_REQUEST_URL,-1)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                get(GET_FOLLOWINGS_REQUEST_URL,0)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로잉을 가져올 때, userId는 정수가 보내줘야 하며 타입 변환이 불가능할 경우 INVALID_USER_INPUT을 반환한다.")
    @Test
    void getFollowingsWithNotLongId() throws Exception {
        mockMvc.perform(
                get(GET_FOLLOWINGS_REQUEST_URL,"a")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉 모두를 가져올 때, userId는 양수여야한다.")
    @Test
    void getAllFollowsWithNonPositiveId() throws Exception {
        mockMvc.perform(
                get(GET_FOLLOWERS_AND_FOLLOWINGS_REQUEST_URL,-1)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                get(GET_FOLLOWERS_AND_FOLLOWINGS_REQUEST_URL,0)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉 모두를 가져올 때, userId는 정수가 보내줘야 하며 타입 변환이 불가능할 경우 INVALID_USER_INPUT을 반환한다.")
    @Test
    void getAllFollowsWithNotLongId() throws Exception {
        mockMvc.perform(
                get(GET_FOLLOWERS_AND_FOLLOWINGS_REQUEST_URL,"a")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉을 생성할 때, fromUserId와 toUserId에는 양수만 올 수 있다.")
    @Test
    void parameterOfAddFollowRequestDtoIsPositive() throws Exception {
        //given
        Follows invalidFollowDto1 = new Follows(-1L, 2L);
        Follows invalidFollowDto2 = new Follows(1L, -2L);
        Follows invalidFollowDto3 = new Follows(-1L, -2L);
        Follows invalidFollowDto4 = new Follows(0, 1L);

        //when then
        mockMvc.perform(
                post(ADD_FOLLOWS_REQUEST_URL, "1")
                    .content(objectMapper.writeValueAsString(invalidFollowDto1))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                post(ADD_FOLLOWS_REQUEST_URL, "1")
                    .content(objectMapper.writeValueAsString(invalidFollowDto2))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                post(ADD_FOLLOWS_REQUEST_URL, "1")
                    .content(objectMapper.writeValueAsString(invalidFollowDto3))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                post(ADD_FOLLOWS_REQUEST_URL, "1")
                    .content(objectMapper.writeValueAsString(invalidFollowDto4))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉을 생성할 때, fromUserId와 toUserId는 필수 값이다.")
    @Test
    void AllParameterOfAddFollowRequestDtoIsRequired() throws Exception {
        mockMvc.perform(
                post(ADD_FOLLOWS_REQUEST_URL, "1")
                    .content("{\"fromUserId\":1}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                post(ADD_FOLLOWS_REQUEST_URL, "1")
                    .content("{\"toUserId\":1}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉을 삭제할 때, fromUserId와 toUserId에는 양수만 올 수 있다.")
    @Test
    void parameterOfDeleteFollowRequestDtoIsPositive() throws Exception {
        //given
        Follows invalidFollowDto1 = new Follows(-1L, 2L);
        Follows invalidFollowDto2 = new Follows(1L, -2L);
        Follows invalidFollowDto3 = new Follows(-1L, -2L);
        Follows invalidFollowDto4 = new Follows(0, 1L);

        //when then
        mockMvc.perform(
                        delete(DELETE_FOLLOWS_REQUEST_URL, "1")
                                .content(objectMapper.writeValueAsString(invalidFollowDto1))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        delete(DELETE_FOLLOWS_REQUEST_URL, "1")
                                .content(objectMapper.writeValueAsString(invalidFollowDto2))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        delete(DELETE_FOLLOWS_REQUEST_URL, "1")
                                .content(objectMapper.writeValueAsString(invalidFollowDto3))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        delete(DELETE_FOLLOWS_REQUEST_URL, "1")
                                .content(objectMapper.writeValueAsString(invalidFollowDto4))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉을 삭제할 때, fromUserId와 toUserId는 필수 값이다.")
    @Test
    void AllParameterOfDeleteFollowRequestDtoIsRequired() throws Exception {
        mockMvc.perform(
                        delete(DELETE_FOLLOWS_REQUEST_URL, "1")
                                .content("{\"fromUserId\":1}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                        delete(DELETE_FOLLOWS_REQUEST_URL, "1")
                                .content("{\"toUserId\":1}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }
}
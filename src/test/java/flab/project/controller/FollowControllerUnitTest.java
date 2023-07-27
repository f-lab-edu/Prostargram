package flab.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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


    @DisplayName("팔로워 목록을 가져올 수 있다.")
    @Test
    void getFollowers() throws Exception {
        //given
        List<User> result = List.of(new User(), new User());
        SuccessResponse<List<User>> response = new SuccessResponse<>(result);

        when(followService.getFollows(1L, GetFollowsType.FOLLOWERS))
            .thenReturn(response);

        //when
        mockMvc.perform(
                get("/users/{userId}/followers", "1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.result").isArray());

    }

    @DisplayName("팔로워를 가져올 때, userId는 양수여야 한다.")
    @Test
    void getFollowersWithNonPositiveId() throws Exception {
        mockMvc.perform(
                get("/users/{userId}/followers",-1)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                get("/users/{userId}/followers",0)
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
                get("/users/{userId}/followers","a")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로잉 목록을 가져올 수 있다.")
    @Test
    void getFollowings() throws Exception {
        //given
        List<User> result = List.of(new User(), new User());
        SuccessResponse<List<User>> response = new SuccessResponse<>(result);

        when(followService.getFollows(1L, GetFollowsType.FOLLOWINGS))
            .thenReturn(response);

        //when then
        mockMvc.perform(
                get("/users/{userId}/followings", "1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.result").isArray());
    }

    @DisplayName("팔로잉을 가져올 때, userId는 양수여야 한다.")
    @Test
    void getFollowingsWithNonPositiveId() throws Exception {
        mockMvc.perform(
                get("/users/{userId}/followings",-1)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                get("/users/{userId}/followings",0)
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
                get("/users/{userId}/followers","a")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉 목록을 한번에 가져올 수 있다.")
    @Test
    void getAllFollows() throws Exception {
        //given
        List<User> result = List.of(new User(), new User());
        SuccessResponse<List<User>> response = new SuccessResponse<>(result);

        when(followService.getFollows(1L, GetFollowsType.ALL))
            .thenReturn(response);

        //when then
        mockMvc.perform(
                get("/users/{userId}/follows/all", "1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.result").isArray());
    }

    @DisplayName("팔로워/팔로잉 모두를 가져올 때, userId는 양수여야한다.")
    @Test
    void getAllFollowsWithNonPositiveId() throws Exception {
        mockMvc.perform(
                get("/users/{userId}/follows/all",-1)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                get("/users/{userId}/follows/all",0)
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
                get("/users/{userId}/follows/all","a")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                get("/users/{userId}/follows/all",0)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉을 생성할 수 있다.")
    @Test
    void postFollow() throws Exception {
        //given
        Follows follows = new Follows(1L, 2L);
        SuccessResponse response = new SuccessResponse();

//        when(followService.postFollow(followRequestDto)) //이렇게 하면 왜 안되지..?
        when(followService.postFollow(any(Follows.class)))
            .thenReturn(response);

        //when then
        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(follows))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));

    }


    @DisplayName("팔로워/팔로잉을 생성할 때, fromUserId와 toUserId에는 양수만 올 수 있다.")
    @Test
    void parameterOfFollowRequestDtoIsPositive() throws Exception {
        //given
        Follows follows1 = new Follows(-1L, 2L);
        Follows follows2 = new Follows(1L, -2L);
        Follows follows3 = new Follows(-1L, -2L);
        Follows follows4 = new Follows(0, 1L);

        //when then
        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(follows1))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(follows2))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(follows3))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(follows4))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉을 생성할 때, fromUserId와 toUserId는 필수 값이다.")
    @Test
    void AllParameterOfFollowRequestDtoIsRequired() throws Exception {
        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content("{\"fromUserId\":1}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content("{\"toUserId\":1}")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }









}
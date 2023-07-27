package flab.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.BaseResponse;
import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.FollowRequestDto;
import flab.project.data.dto.User;
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
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FollowService followService;


    @DisplayName("팔로워 목록을 가져온다.")
    @Test
    void getFollowers() throws Exception {
        //given
        List<User> result = List.of(new User(), new User());
        BaseResponse<List<User>> response = new SuccessResponse<>(result);

        when(followService.getFollows(1L, GetFollowsType.FOLLOWERS))
            .thenReturn(response);

        //when
        mockMvc.perform(
                get("/users/{userId}/followers","1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.result").isArray());

    }

    @DisplayName("팔로잉 목록을 가져온다.")
    @Test
    void getFollowings() throws Exception {
        //given
        List<User> result = List.of(new User(), new User());
        SuccessResponse<List<User>> response = new SuccessResponse<>(result);

        when(followService.getFollows(1L, GetFollowsType.FOLLOWINGS))
            .thenReturn(response);

        //when then
        mockMvc.perform(
                get("/users/{userId}/followings","1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.result").isArray());
    }

    @DisplayName("팔로워/팔로잉을 생성한다.")
    @Test
    void postFollow() throws Exception {
        //given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L, 2L);
        SuccessResponse response = new SuccessResponse();

//        when(followService.postFollow(followRequestDto)) //이렇게 하면 왜 안되지..?
        when(followService.postFollow(any(FollowRequestDto.class)))
            .thenReturn(response);
        System.out.println("followRequestDto = " + followRequestDto);
        System.out.println("response = " + response);
        //when then

        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(followRequestDto))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));

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
                get("/users/{userId}/follows/all","1")
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.result").isArray());
    }

    @DisplayName("팔로워/팔로잉을 생성할 때, fromUserId와 toUserId에는 양수만 올 수 있다.")
    @Test
    void parameterIsPositiveOfFollowRequestDto() throws Exception {
        //given
        FollowRequestDto followRequestDto1 = new FollowRequestDto(-1L, 2L);
        FollowRequestDto followRequestDto2 = new FollowRequestDto(1L, -2L);
        FollowRequestDto followRequestDto3 = new FollowRequestDto(-1L, -2L);

        FailResponse failResponse = new FailResponse(ResponseEnum.ILLEGAL_ARGUMENT);
        //when then
        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(followRequestDto1))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.ILLEGAL_ARGUMENT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.ILLEGAL_ARGUMENT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.ILLEGAL_ARGUMENT.getMessage()));

        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(followRequestDto2))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.ILLEGAL_ARGUMENT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.ILLEGAL_ARGUMENT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.ILLEGAL_ARGUMENT.getMessage()));

        mockMvc.perform(
                post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(followRequestDto3))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.ILLEGAL_ARGUMENT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.ILLEGAL_ARGUMENT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.ILLEGAL_ARGUMENT.getMessage()));
    }

    //todo fromUserId나 toUserId중에 하나 이상이 전달되지 않았을 때는 어떻게 테스트하지?

}
package flab.project.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.data.dto.model.Follows;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class FollowControllerIntegrationTest {

    @Autowired
    FollowController followController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("팔로워/팔로잉을 생성할 때, fromUserId와 toUserId에는 같은 값이 올 수 없다.")
    @Test
    void parameterUserIdOfFollowRequestDtoIsPositive() throws Exception {
        //given
        Follows follows = new Follows(1L, 1L);

        //when, Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(follows))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));

    }

    @DisplayName("팔로워/팔로잉을 생성할 때, 존재하지 않는 userId가 입력될 수 없다.")
    @Test
    void parameterUserIdOfFollowRequestDtoMustExist() throws Exception {
        //given
        long nonExistUserId1 = 99;
        long nonExistUserId2 = 100;

        Follows follows = new Follows(nonExistUserId1, nonExistUserId2);

        //when, Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(follows))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.INVALID_USER_INPUT.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.INVALID_USER_INPUT.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("팔로워/팔로잉을 생성할 때, 같은 요청이 중복해서 전송될 경우 에러를 반환한다.")
    @Test
    void CanNotDuplicateRequest() throws Exception {
        //given
        Follows follows1 = new Follows(1L, 2L);

        followController.postFollow(follows1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/{userId}/follows", "1")
                    .content(objectMapper.writeValueAsString(follows1))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andDo(print())
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.DUPLICATE_REQUEST.isSuccess()))
            .andExpect(jsonPath("$.code").value(ResponseEnum.DUPLICATE_REQUEST.getCode()))
            .andExpect(jsonPath("$.message").value(ResponseEnum.DUPLICATE_REQUEST.getMessage()));
    }



}

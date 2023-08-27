package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.AddInterest;
import flab.project.facade.InterestFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InterestController.class)
class InterestControllerTest {

    private static final String ADD_INTERST_API_URL = "/users/{userId}/interests";
    private static final String DELETE_INTEREST_API_URL = "/users/{userId}/interests";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private InterestFacade interestFacade;

    @DisplayName("관심사를 설정할 수 있다.")
    @Test
    void addInterest() throws Exception {
        AddInterest addInterest = new AddInterest(1L, "test-interest");
        given(interestFacade.addInterest(any(AddInterest.class)))
                .willReturn(new SuccessResponse());

        mockMvc.perform(
                        post(ADD_INTERST_API_URL, 1)
                                .content(objectMapper.writeValueAsString(addInterest))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

    }

    @DisplayName("관심사 추가 API에서 userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenAddInterest() throws Exception {
        AddInterest addInterestWithinvalidUserId1 = new AddInterest(-1L, "test");
        mockMvc.perform(
                        post(ADD_INTERST_API_URL, -1)
                                .content(objectMapper.writeValueAsString(addInterestWithinvalidUserId1))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));


        AddInterest addInterestWithinvalidUserId2 = new AddInterest(0L, "test");
        mockMvc.perform(
                        post(ADD_INTERST_API_URL, 0, "test")
                                .content(objectMapper.writeValueAsString(addInterestWithinvalidUserId2))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("관심사 추가 API에서 interestName은 최대 15글자까지만 허용된다.")
    @Test
    void interestNameCanHaveMax15LenthStringWhenAddInterest() throws Exception {
        final String STRING_LENGTH_15 = "ABCDEFGHIJKLMNO";
        final String STRING_LENGTH_16 = "ABCDEFGHIJKLMNOP";

        given(interestFacade.addInterest(any(AddInterest.class)))
                .willReturn(new SuccessResponse());

        assertThat(STRING_LENGTH_15.length()).isEqualTo(15);
        assertThat(STRING_LENGTH_16.length()).isEqualTo(16);


        AddInterest addInterestWith15LengthString = new AddInterest(1L, STRING_LENGTH_15);
        mockMvc.perform(
                        post(ADD_INTERST_API_URL, 1)
                                .content(objectMapper.writeValueAsString(addInterestWith15LengthString))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        AddInterest addInterestWith16LengthString = new AddInterest(1L, STRING_LENGTH_16);
        mockMvc.perform(
                        post(ADD_INTERST_API_URL, 1)
                                .content(objectMapper.writeValueAsString(addInterestWith16LengthString))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }


    @DisplayName("관심사를 제거할 수 있다.")
    @Test
    void deleteInterest() throws Exception {
        given(interestFacade.deleteInterest(anyLong(), anyLong()))
                .willReturn(new SuccessResponse());

        mockMvc.perform(
                        delete(DELETE_INTEREST_API_URL, 1)
                                .param("hashtagId", "1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

    }

    @DisplayName("관심사 제거 API에서 userId는 양수여야 한다.")
    @Test
    void userIdMustBePositiveWhenDeleteInterest() throws Exception {
        mockMvc.perform(
                        delete(DELETE_INTEREST_API_URL, -1)
                                .param("hashtagId", "1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));


        mockMvc.perform(
                        delete(DELETE_INTEREST_API_URL, 0)
                                .param("hashtagId", "1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }
}
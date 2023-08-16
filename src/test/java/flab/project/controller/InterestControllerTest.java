package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.UpdateInterest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InterestController.class)
class InterestControllerTest {

    private static final String ADD_INTERST_API_URL = "/users/{userId}/interests";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private InterestFacade interestFacade;

    @DisplayName("괌심사를 설정할 수 있다.")
    @Test
    void addInterest() throws Exception {
        given(interestFacade.addInterest(any(UpdateInterest.class)))
                .willReturn(new SuccessResponse());
        UpdateInterest updateInterest = new UpdateInterest(1L, "test-interest");

        mockMvc.perform(
                        post(ADD_INTERST_API_URL, 1)
                                .content(objectMapper.writeValueAsString(updateInterest))
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
        given(interestFacade.addInterest(any(UpdateInterest.class)))
                .willReturn(new SuccessResponse());
        UpdateInterest updateInterest1 = new UpdateInterest(-1L, "test");

        mockMvc.perform(
                        post(ADD_INTERST_API_URL, -1)
                                .content(objectMapper.writeValueAsString(updateInterest1))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));

        UpdateInterest updateInterest2 = new UpdateInterest(0L, "test");
        mockMvc.perform(
                        post(ADD_INTERST_API_URL, 0, "test")
                                .content(objectMapper.writeValueAsString(updateInterest2))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

    @DisplayName("관심사 추가 API에서 interestName은 최대 15글자 까지만 허용된다.")
    @Test
    void interestNameCanHaveMax15LenthStringWhenAddInterest() throws Exception {
        final String STRING_LENGTH_15 = "ABCDEFGHIJKLMNO";
        final String STRING_LENGTH_16 = "ABCDEFGHIJKLMNOP";

        given(interestFacade.addInterest(any(UpdateInterest.class)))
                .willReturn(new SuccessResponse());

        assertThat(STRING_LENGTH_15.length()).isEqualTo(15);
        assertThat(STRING_LENGTH_16.length()).isEqualTo(16);


        UpdateInterest updateInterestWith15LengthString = new UpdateInterest(1L, STRING_LENGTH_15);
        mockMvc.perform(
                        post(ADD_INTERST_API_URL, 1)
                                .content(objectMapper.writeValueAsString(updateInterestWith15LengthString))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS.getMessage()));

        UpdateInterest updateInterestWith16LengthString = new UpdateInterest(1L, STRING_LENGTH_16);
        mockMvc.perform(
                        post(ADD_INTERST_API_URL, 1)
                                .content(objectMapper.writeValueAsString(updateInterestWith16LengthString))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.isSuccess").value(INVALID_USER_INPUT.isSuccess()))
                .andExpect(jsonPath("$.code").value(INVALID_USER_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(INVALID_USER_INPUT.getMessage()));
    }

}
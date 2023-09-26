package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.common.Constraints;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddPollPostRequest;
import flab.project.template.PostFacadeTemplate;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static flab.project.common.Constraints.*;
import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PollPostController.class)
public class PollPostControllerTest {
    private static final String ADD_POLL_POST_REQUEST_URL = "/posts/poll-post";
    private static final String validPostContent = "게시물 내용입니다";
    private static final String validSubject = "통계 주제입니다";
    private static final LocalDate validStartDate = LocalDate.now();
    private static final LocalDate validEndDate = LocalDate.now().plusDays(1);
    private static final Set<String> validHashTagNames = Set.of("#test1", "#test2");
    private static final Set<String> validOptionContents = Set.of("content1", "content2", "content3", "content4");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostFacadeTemplate postFacadeTemplate;

    @DisplayName("통계 게시물을 생성할 수 있다.")
    @Test
    void addPollPost() throws Exception {
        // given
        AddPollPostRequest validPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        given(postFacadeTemplate.addPost(anyLong(), any(AddPollPostRequest.class)))
                .willReturn(new SuccessResponse());

        // when & then
        validateAddPollPostRequest(validPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isOk(), SUCCESS);
    }

    @DisplayName("통계 게시물을 생성할 때, 시작 날짜가 오늘이여도 된다.")
    @Test
    void addPollPost_todayStartDate() throws Exception {
        // given
        AddPollPostRequest validPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(LocalDate.now())
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        given(postFacadeTemplate.addPost(anyLong(), any(AddPollPostRequest.class)))
                .willReturn(new SuccessResponse());

        // when & then
        validateAddPollPostRequest(validPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isOk(), SUCCESS);
    }

    @DisplayName("통계 게시물을 생성할 때, 종료 날짜가 오늘이여도 된다.")
    @Test
    void addPollPost_todayEndDate() throws Exception {
        // given
        LocalDate today = LocalDate.now();
        AddPollPostRequest validPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(today)
                .endDate(today)
                .allowMultipleVotes(true)
                .build();

        given(postFacadeTemplate.addPost(anyLong(), any(AddPollPostRequest.class)))
                .willReturn(new SuccessResponse());

        // when & then
        validateAddPollPostRequest(validPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isOk(), SUCCESS);
    }

    @DisplayName("통계 게시물을 생성할 때, 비어 있는 postContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withEmptyPostContent() throws Exception {
        // given
        String emptyPostContent = "";
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(emptyPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 공백으로만 이루어진 postContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withOnlyBlankPostContent() throws Exception {
        // given
        String onlyBlankPostContent = "   ";
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(onlyBlankPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 최대 길이를 초과한 postContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withExceedMaxLengthOfPostContent() throws Exception {
        // given
        String postContentExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_POST_CONTENTS + 1);
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(postContentExceededMaxLength)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 비어 있는 subject가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withEmptyPollSubject() throws Exception {
        // given
        String emptyPollSubject = "";
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(emptyPollSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 공백으로만 이루어진 subject가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withOnlyBlankPollSubject() throws Exception {
        // given
        String onlyBlankPollSubject = "   ";
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(onlyBlankPollSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 최대 길이를 초과한 subject가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withExceedMaxLengthOfPollSubject() throws Exception {
        // given
        String pollSubjectExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_POLL_SUBJECT + 1);
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(pollSubjectExceededMaxLength)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 비어 있는 문자열의 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withEmptyHashTagName() throws Exception {
        // given
        Set<String> hashTagNamesWithEmpty = Set.of("", "#test");
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(hashTagNamesWithEmpty)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 공백으로만 이루어진 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withOnlyBlankHashTagName() throws Exception {
        // given
        Set<String> hashTagNamesWithOnlyBlank = Set.of("    ", "#test");
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(hashTagNamesWithOnlyBlank)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, hashTagNames가 최대 개수를 초과하면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withExceedMaxSizeOfHashTagNames() throws Exception {
        // given
        Set<String> hashTagNamesExceededMaxSize = Set.of("#test1", "#test2", "#test3", "#test4", "#test5", "#test6");
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(hashTagNamesExceededMaxSize)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        assertThat(hashTagNamesExceededMaxSize).hasSizeGreaterThan(MAX_SIZE_OF_POST_HASHTAGS);
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, hashTagNames 중 최대 길이를 초과한 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withExceedMaxLengthOfHashTagName() throws Exception {
        // given
        String hashTagNameExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_HASHTAGS + 1);
        Set<String> invalidHashTagNames = Set.of(hashTagNameExceededMaxLength);

        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(invalidHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 비어 있는 문자열의 optionContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withEmptyOptionContents() throws Exception {
        // given
        Set<String> optionContentsWithEmpty = Set.of("", "#test");
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(optionContentsWithEmpty)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, 공백으로만 이루어진 optionContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withOnlyBlankOptionContents() throws Exception {
        // given
        Set<String> optionContentsWithOnlyBlank = Set.of("    ", "#test");
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(optionContentsWithOnlyBlank)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, optionContents 요소가 2가 아니면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_moreThanMaxSizeOfOptionContents() throws Exception {
        // given
        Set<String> optionContentsExceededMaxSize = createDummyStringSet(MAX_SIZE_OF_POLL_POST_OPTION_COUNT + 1);

        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(optionContentsExceededMaxSize)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        assertThat(optionContentsExceededMaxSize).hasSizeGreaterThan(Constraints.MAX_SIZE_OF_POLL_POST_OPTION_COUNT);
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @Test
    void addPollPost_lessThanMinSizeOfOptionContents() throws Exception {
        // given
        Set<String> optionContentsLessThanMinSize = createDummyStringSet(MIN_SIZE_OF_POLL_POST_OPTION_COUNT - 1);

        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(optionContentsLessThanMinSize)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        assertThat(optionContentsLessThanMinSize).hasSizeLessThan(Constraints.MIN_SIZE_OF_POLL_POST_OPTION_COUNT);
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, optionContents 요소중 최대 길이를 초과한 요소가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_exceedMaxLengtheOfOptionContents() throws Exception {
        // given
        String optionContentExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_POLL_POST_OPTION_CONTENT + 1);
        Set<String> invalidOptionContents = Set.of("test1", "test2", optionContentExceededMaxLength);

        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(invalidOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, startDate가 오늘 이전이면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withBeforeStartDateOfToday() throws Exception {
        // given
        LocalDate startDateBeforeToday = LocalDate.now().minusDays(1);
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(startDateBeforeToday)
                .endDate(validEndDate)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("통계 게시물을 생성할 때, endDate가 오늘 이전이면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addPollPost_withBeforeEndDateOfToday() throws Exception {
        // given
        LocalDate endDateBeforeToday = LocalDate.now().minusDays(1);
        AddPollPostRequest invalidPollPostRequest = AddPollPostRequest.builder()
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .optionContents(validOptionContents)
                .subject(validSubject)
                .startDate(validStartDate)
                .endDate(endDateBeforeToday)
                .allowMultipleVotes(true)
                .build();

        // when & then
        validateAddPollPostRequest(invalidPollPostRequest, ADD_POLL_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    private Set<String> createDummyStringSet(int size) {
        Set<String> hashTagNames = new HashSet<>();

        for (int i = 0; i < size; i++) {
            hashTagNames.add("#test" + i);
        }

        return hashTagNames;
    }

    private void validateAddPollPostRequest(AddPollPostRequest pollPostRequest, String requestUrl, ResultMatcher statusCode, ResponseEnum responseEnum) throws Exception {
        requestAddPollPost(pollPostRequest, requestUrl)
                .andExpect(statusCode)
                .andExpect(jsonPath("$.isSuccess").value(responseEnum.isSuccess()))
                .andExpect(jsonPath("$.code").value(responseEnum.getCode()))
                .andExpect(jsonPath("$.message").value(responseEnum.getMessage()));
    }

    private ResultActions requestAddPollPost(AddPollPostRequest pollPostRequest, String requestUrl) throws Exception {
        return mockMvc.perform(
                        post(requestUrl)
                                .content(objectMapper.writeValueAsString(pollPostRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
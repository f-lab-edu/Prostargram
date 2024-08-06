package flab.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.common.Constraints;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.enums.PostType;
import flab.project.domain.post.model.AddDebatePostRequest;
import flab.project.domain.post.controller.DebatePostController;
import flab.project.domain.post.model.DebatePost;
import flab.project.domain.post.template.PostFacadeTemplate;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static flab.project.common.Constraints.*;
import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DebatePostController.class)
class DebatePostControllerTest {

    private static final String ADD_DEBATE_POST_REQUEST_URL = "/posts/debate";
    private static final String validPostContent = "게시물 내용입니다";
    private static final Set<String> validHashTagNames = Set.of("#test1", "#test2");
    private static final Set<String> validOptionContents = Set.of("content1", "content2");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostFacadeTemplate postFacadeTemplate;
    @MockBean
    private FanOutService fanOutService;

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 수 있다.")
    @Test
    void addDebatePost() throws Exception {
        // given
        AddDebatePostRequest validDebatePostRequest = createAddDebatePost(validPostContent, validHashTagNames, validOptionContents);

        // when & then
        DebatePost mockDebatePost = DebatePost.builder()
                .postId(1L)
                .userId(1L)
                .content(validPostContent)
                .hashTagNames(validHashTagNames)
                .postType(PostType.DEBATE)
                .likeCount(0)
                .commentCount(0)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .isLike(false)
                .isFollow(false)
                .options(Set.of())
                .selectedOptionId(null)
                .build();

        given(postFacadeTemplate.addPost(anyLong(), any(AddDebatePostRequest.class))).willReturn(mockDebatePost);
        validateAddDebatePostRequest(validDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isOk(), SUCCESS);
        then(postFacadeTemplate).should().addPost(anyLong(), any(AddDebatePostRequest.class));
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, 비어 있는 postContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withEmptyPostContent() throws Exception {
        // given
        String emptyPostContent = "";
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(emptyPostContent, validHashTagNames, validOptionContents);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, 공백으로만 이루어진 postContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withOnlyBlankPostContent() throws Exception {
        // given
        String onlyBlankPostContent = "   ";
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(onlyBlankPostContent, validHashTagNames, validOptionContents);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, 최대 길이를 초과한 postContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withExceedMaxLengthOfPostContent() throws Exception {
        // given
        String postContentExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_POST_CONTENTS + 1);
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(postContentExceededMaxLength, validHashTagNames, validOptionContents);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, 비어 있는 문자열의 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withEmptyHashTagName() throws Exception {
        // given
        Set<String> hashTagNamesWithEmpty = Set.of("", "#test");
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(validPostContent, hashTagNamesWithEmpty, validOptionContents);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, 공백으로만 이루어진 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withOnlyBlankHashTagName() throws Exception {
        // given
        Set<String> hashTagNamesWithOnlyBlank = Set.of("    ", "#test");
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(validPostContent, hashTagNamesWithOnlyBlank, validOptionContents);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, hashTagNames가 최대 개수를 초과하면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withExceedMaxSizeOfHashTagNames() throws Exception {
        // given
        Set<String> hashTagNamesExceededMaxSize = createDummyStringSet(MAX_SIZE_OF_POST_HASHTAGS + 1);
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(validPostContent, hashTagNamesExceededMaxSize, validOptionContents);

        // when & then
        assertThat(hashTagNamesExceededMaxSize).hasSizeGreaterThan(MAX_SIZE_OF_POST_HASHTAGS);
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, hashTagNames 중 최대 길이를 초과한 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withExceedMaxLengthOfHashTagName() throws Exception {
        // given
        String hashTagNameExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_HASHTAGS + 1);
        Set<String> invalidHashTagNames = Set.of(hashTagNameExceededMaxLength);

        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(validPostContent, invalidHashTagNames, validOptionContents);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, 비어 있는 문자열의 optionContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withEmptyOptionContents() throws Exception {
        // given
        Set<String> optionContentsWithEmpty = Set.of("", "#test");
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(validPostContent, validHashTagNames, optionContentsWithEmpty);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, 공백으로만 이루어진 optionContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_withOnlyBlankOptionContents() throws Exception {
        // given
        Set<String> optionContentsWithOnlyBlank = Set.of("    ", "#test");
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(validPostContent, validHashTagNames, optionContentsWithOnlyBlank);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, optionContents 요소가 2가 아니면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_invalidSizeOfOptionContents() throws Exception {
        // given
        Set<String> optionContentsInvalidSize = createDummyStringSet(FIXED_SIZE_OF_DEBATE_POST_OPTIONS + 1);
        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(validPostContent, validHashTagNames, optionContentsInvalidSize);

        // when & then
        assertThat(optionContentsInvalidSize.size()).isNotEqualTo(Constraints.FIXED_SIZE_OF_DEBATE_POST_OPTIONS);
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);

        // given
        Set<String> anotherOptionContentsInvalidSize = createDummyStringSet(FIXED_SIZE_OF_DEBATE_POST_OPTIONS - 1);
        AddDebatePostRequest anotherInvalidDebatePostRequest = createAddDebatePost(validPostContent, validHashTagNames, anotherOptionContentsInvalidSize);

        // when & then
        assertThat(optionContentsInvalidSize.size()).isNotEqualTo(Constraints.FIXED_SIZE_OF_DEBATE_POST_OPTIONS);
        validateAddDebatePostRequest(anotherInvalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @WithMockUser(username = "1")
    @DisplayName("토론 게시물을 생성할 때, optionContents 요소중 최대 길이를 초과한 요소가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addDebatePost_exceedMaxLengtheOfOptionContents() throws Exception {
        // given
        String optionContentExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_DEBATE_POST_OPTION_CONTENT + 1);
        Set<String> invalidOptionContents = Set.of(optionContentExceededMaxLength);

        AddDebatePostRequest invalidDebatePostRequest = createAddDebatePost(validPostContent, validHashTagNames, invalidOptionContents);

        // when & then
        validateAddDebatePostRequest(invalidDebatePostRequest, ADD_DEBATE_POST_REQUEST_URL, status().isBadRequest(), INVALID_USER_INPUT);
    }

    private Set<String> createDummyStringSet(int size) {
        Set<String> hashTagNames = new HashSet<>();

        for (int i = 0; i < size; i++) {
            hashTagNames.add("#test" + i);
        }

        return hashTagNames;
    }

    private AddDebatePostRequest createAddDebatePost(String postContent, Set<String> hashTagNames, Set<String> optionContents) {
        return AddDebatePostRequest.builder()
                .content(postContent)
                .hashTagNames(hashTagNames)
                .optionContents(optionContents)
                .build();
    }

    private void validateAddDebatePostRequest(AddDebatePostRequest debatePostRequest, String requestUrl, ResultMatcher statusCode, ResponseEnum responseEnum) throws Exception {
        requestAddDebatePost(debatePostRequest, requestUrl)
                .andExpect(statusCode)
                .andExpect(jsonPath("$.isSuccess").value(responseEnum.isSuccess()))
                .andExpect(jsonPath("$.code").value(responseEnum.getCode()))
                .andExpect(jsonPath("$.message").value(responseEnum.getMessage()));
    }

    private ResultActions requestAddDebatePost(AddDebatePostRequest debatePostRequest, String requestUrl) throws Exception {
        return mockMvc.perform(
                        post(requestUrl)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(debatePostRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
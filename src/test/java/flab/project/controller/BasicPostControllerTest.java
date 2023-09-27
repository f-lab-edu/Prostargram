package flab.project.controller;

import static flab.project.common.Constraints.*;
import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddBasicPostRequest;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import flab.project.template.PostFacadeTemplate;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

@WebMvcTest(controllers = BasicPostController.class)
class BasicPostControllerTest {

    private static final String ADD_BASIC_POST_REQUEST_URL = "/posts/basic-post";
    private static final String validPostContent = "게시물 내용입니다";
    private static final Set<String> validHashTagNames = Set.of("#test1", "#test2");
    private static final MockMultipartFile validContentImage = createMockMultiPartFile();
    private static final List<MockMultipartFile> validContentImages = List.of(validContentImage, validContentImage);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostFacadeTemplate postFacadeTemplate;

    @DisplayName("일반 게시물을 생성할 수 있다.")
    @Test
    void addBasicPost() throws Exception {
        // given
        AddBasicPostRequest validBasicPost = createAddBasicPostRequest(validPostContent, validHashTagNames);

        MockMultipartFile dto = createMultiPartDto("basicPost", validBasicPost);

        // when & then
        validateAddBasicPost(validContentImages, dto, status().isOk(), SUCCESS);
        then(postFacadeTemplate).should().addPost(anyLong(), any(AddBasicPostRequest.class));
    }

    @DisplayName("일반 게시물을 생성할 때, contentImages가 없으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withoutContentImages() throws Exception {
        // given
        AddBasicPostRequest validBasicPost = createAddBasicPostRequest(validPostContent, validHashTagNames);
        MockMultipartFile dto = createMultiPartDto("basicPost", validBasicPost);

        // when & then
        validateAddBasicPost(List.of(), dto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, postContent가 비어있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withEmptyPostContent() throws Exception {
        // given
        String emptyPostContent = "";
        AddBasicPostRequest invalidBasicPostRequest = createAddBasicPostRequest(emptyPostContent, validHashTagNames);
        MockMultipartFile invalidDto = createMultiPartDto("basicPost", invalidBasicPostRequest);

        // when & then
        validateAddBasicPost(validContentImages, invalidDto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, postContent가 공백으로만 이루어져 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withOnlyBlankPostContent() throws Exception {
        // given
        String onlyBlankPostContent = "   ";
        AddBasicPostRequest invalidBasicPostRequest = createAddBasicPostRequest(onlyBlankPostContent, validHashTagNames);
        MockMultipartFile invalidDto = createMultiPartDto("basicPost", invalidBasicPostRequest);

        // when & then
        validateAddBasicPost(validContentImages, invalidDto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, 최대 길이를 초과한 postContent가 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withExceedMaxLengthOfPostContent() throws Exception {
        // given
        String postContentExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_POST_CONTENTS + 1);
        AddBasicPostRequest invalidBasicPostRequest = createAddBasicPostRequest(postContentExceededMaxLength, validHashTagNames);
        MockMultipartFile invalidDto = createMultiPartDto("basicPost", invalidBasicPostRequest);

        // when & then
        validateAddBasicPost(validContentImages, invalidDto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, hashTagNames 중 비어 있는 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withEmptyHashTagNames() throws Exception {
        // given
        Set<String> hashTagNamesWithEmpty = Set.of("#test1", "");
        AddBasicPostRequest invalidBasicPostRequest = createAddBasicPostRequest(validPostContent, hashTagNamesWithEmpty);
        MockMultipartFile invalidDto = createMultiPartDto("basicPost", invalidBasicPostRequest);

        // when & then
        validateAddBasicPost(validContentImages, invalidDto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, hashTagNames 중 공백으로만 이루어진 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withOnlyBlankHashTagNames() throws Exception {
        // given
        Set<String> hashTagNamesWithOnlyBlank = Set.of("#test1", "      ");
        AddBasicPostRequest invalidBasicPost = createAddBasicPostRequest(validPostContent, hashTagNamesWithOnlyBlank);
        MockMultipartFile invalidDto = createMultiPartDto("basicPost", invalidBasicPost);

        // when & then
        validateAddBasicPost(validContentImages, invalidDto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, hashTagNames가 최대 개수를 초과하면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withExceedMaxSizeOfHashTagNames() throws Exception {
        // given
        Set<String> hashTagNamesExceededMaxSize = createHashTagNames(MAX_SIZE_OF_POST_HASHTAGS + 1);
        AddBasicPostRequest invalidBasicPost = createAddBasicPostRequest(validPostContent, hashTagNamesExceededMaxSize);
        MockMultipartFile invalidDto = createMultiPartDto("basicPost", invalidBasicPost);

        // when & then
        assertThat(hashTagNamesExceededMaxSize).hasSizeGreaterThan(MAX_SIZE_OF_POST_HASHTAGS);
        validateAddBasicPost(validContentImages, invalidDto, status().isBadRequest(), INVALID_USER_INPUT);
    }



    @DisplayName("일반 게시물을 생성할 때, hashTagNames 중 최대 길이를 초과한 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withExceedMaxLengthOfHashTagName() throws Exception {
        // given
        String hashTagNameExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_HASHTAGS + 1);
        Set<String> invalidHashTagNames = Set.of(hashTagNameExceededMaxLength);

        AddBasicPostRequest invalidBasicPost = createAddBasicPostRequest(validPostContent, invalidHashTagNames);
        MockMultipartFile invalidDto = createMultiPartDto("basicPost", invalidBasicPost);

        // when & then
        validateAddBasicPost(validContentImages, invalidDto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    private MockMultipartFile createMultiPartDto(String paramName, AddBasicPostRequest basicPostWithoutEmptyContent) throws JsonProcessingException {
        return new MockMultipartFile(
                paramName,
                paramName,
                "application/json",
                objectMapper.writeValueAsString(basicPostWithoutEmptyContent).getBytes(StandardCharsets.UTF_8)
        );
    }

    private Set<String> createHashTagNames(int size) {
        Set<String> hashTagNames = new HashSet<>();

        for (int i = 0; i < size; i++) {
            hashTagNames.add("#test" + i);
        }

        return hashTagNames;
    }

    private AddBasicPostRequest createAddBasicPostRequest(String postContent, Set<String> hashTagNames) {
        return AddBasicPostRequest.builder()
                .content(postContent)
                .hashTagNames(hashTagNames)
                .build();
    }

    private static MockMultipartFile createMockMultiPartFile() {
        return new MockMultipartFile(
                "contentImages",
                "test.jpg",
                "text/plain",
                "test file".getBytes()
        );
    }

    private void validateAddBasicPost(List<MockMultipartFile> multipartFiles, MockMultipartFile dto, ResultMatcher statusCode, ResponseEnum responseEnum) throws Exception {
        requestAddBasicPost(multipartFiles, dto)
                .andExpect(statusCode)
                .andExpect(jsonPath("$.isSuccess").value(responseEnum.isSuccess()))
                .andExpect(jsonPath("$.code").value(responseEnum.getCode()))
                .andExpect(jsonPath("$.message").value(responseEnum.getMessage()));
    }

    private ResultActions requestAddBasicPost(List<MockMultipartFile> multipartFiles, MockMultipartFile dto) throws Exception {
        return mockMvc.perform(
                        setMultipartFiles(multipartFiles, dto)
                )
                .andDo(print());
    }

    private MockMultipartHttpServletRequestBuilder setMultipartFiles(List<MockMultipartFile> multipartFiles, MockMultipartFile dto) {
        MockMultipartHttpServletRequestBuilder request = multipart(HttpMethod.POST, ADD_BASIC_POST_REQUEST_URL);

        request.file(dto);
        for (MockMultipartFile multipartFile : multipartFiles) {
            request.file(multipartFile);
        }

        return request;
    }
}
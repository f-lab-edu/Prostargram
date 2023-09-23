package flab.project.controller;

import static flab.project.config.baseresponse.ResponseEnum.INVALID_USER_INPUT;
import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.common.FileStorage.FileStorage;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.facade.PostFacade;
import flab.project.service.PostHashTagService;
import flab.project.service.PostImageService;
import flab.project.service.PostService;

import java.nio.charset.StandardCharsets;
import java.util.List;

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

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    private static final String ADD_BASIC_POST_REQUEST_URL = "/posts/basic-post";
    private static final String validPostContent = "게시물 내용입니다";
    private static final List<String> validHashTagNames = List.of("#test1", "#test2");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostFacade postFacade;
    @MockBean
    private PostService postService;
    @MockBean
    private PostHashTagService PostHashTagService;
    @MockBean
    private FileStorage fileStorage;
    @MockBean
    private PostImageService postImageService;

    @DisplayName("일반 게시물을 생성할 수 있다.")
    @Test
    void addBasicPost() throws Exception {
        // given
        AddBasicPostRequest validBasicPost = createAddBasicPostRequest(validPostContent, validHashTagNames);
        MockMultipartFile dto = createMultiPartDto("basicPost", validBasicPost);

        given(postFacade.addBasicPost(anyLong(), any(AddBasicPostRequest.class), anyList()))
                .willReturn(new SuccessResponse<>());

        // when & then
        validateAddBasicPost(List.of(createMockMultiPartFile(), createMockMultiPartFile()), dto, status().isOk(), SUCCESS);
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

    @DisplayName("일반 게시물을 생성할 때, content가 \"\"면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withEmptyPostContent() throws Exception {
        // given
        String invalidPostContent = "";
        AddBasicPostRequest basicPostWithEmptyContent = createAddBasicPostRequest(invalidPostContent, validHashTagNames);
        MockMultipartFile dto = createMultiPartDto("basicPost", basicPostWithEmptyContent);

        // when & then
        validateAddBasicPost(List.of(createMockMultiPartFile(), createMockMultiPartFile()), dto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, content가 공백으로만 이루어져 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withOnlyBlankPostContent() throws Exception {
        // given
        String invalidPostContent = "   ";
        AddBasicPostRequest basicPostWithOnlyBlankContent = createAddBasicPostRequest(invalidPostContent, validHashTagNames);
        MockMultipartFile dto = createMultiPartDto("basicPost", basicPostWithOnlyBlankContent);

        // when & then
        validateAddBasicPost(List.of(createMockMultiPartFile(), createMockMultiPartFile()), dto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, hashTagNames 중 \"\"인 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withEmptyHashTagNames() throws Exception {
        // given
        List<String> invalidHashTagNames = List.of("#test1", " ");
        AddBasicPostRequest basicPostWithEmptyHashTagNames = createAddBasicPostRequest(validPostContent, invalidHashTagNames);
        MockMultipartFile dto = createMultiPartDto("basicPost", basicPostWithEmptyHashTagNames);

        // when & then
        validateAddBasicPost(List.of(createMockMultiPartFile(), createMockMultiPartFile()), dto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    @DisplayName("일반 게시물을 생성할 때, hashTagNames 중 공백으로만 이루어진 hashTagName이 있으면 INVALID_USER_INPUT을 반환한다.")
    @Test
    void addBasicPost_withOnlyBlankHashTagNames() throws Exception {
        // given
        List<String> invalidHashTagNames = List.of("#test1", " ");
        AddBasicPostRequest basicPostWithEmptyHashTagNames = createAddBasicPostRequest(validPostContent, invalidHashTagNames);
        MockMultipartFile dto = createMultiPartDto("basicPost", basicPostWithEmptyHashTagNames);

        // when & then
        validateAddBasicPost(List.of(createMockMultiPartFile(), createMockMultiPartFile()), dto, status().isBadRequest(), INVALID_USER_INPUT);
    }

    private MockMultipartFile createMultiPartDto(String paramName, AddBasicPostRequest basicPostWithoutEmptyContent) throws JsonProcessingException {
        return new MockMultipartFile(
                paramName,
                paramName,
                "application/json",
                objectMapper.writeValueAsString(basicPostWithoutEmptyContent).getBytes(StandardCharsets.UTF_8)
        );
    }

    private AddBasicPostRequest createAddBasicPostRequest(String postContent, List<String> hashTagNames) {
        return AddBasicPostRequest.builder()
                .content(postContent)
                .hashTagNames(hashTagNames)
                .build();
    }

    private MockMultipartFile createMockMultiPartFile() {
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
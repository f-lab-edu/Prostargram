package flab.project.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.common.FileStorage.FileStorage;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.facade.PostFacade;
import flab.project.mapper.PostHashTagMapper;
import flab.project.service.PostHashTagService;
import flab.project.service.PostImageService;
import flab.project.service.PostService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    private static final String ADD_BASIC_POST_REQUEST_URL = "/posts/basic-post";
    private static final String validPostContent = "게시물 내용입니다";
    private static final List<String> validHashTagNames = List.of("#test1", "#test2");
    private static final AddBasicPostRequest validBasicPost
            = AddBasicPostRequest.builder()
            .content(validPostContent)
            .hashTagNames(validHashTagNames)
            .build();

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

    @Test
    void addBasicPost() throws Exception {
        String postContent = "게시물 내용입니다";
        List<String> hashTagNames = List.of("#test1", "#test2");

        MockMultipartFile multipartFile = new MockMultipartFile(
                "postImgae",
                "test.txt",
                "text/plain",
                "test file".getBytes()
        );

        MockMultipartFile multipartFile2 = new MockMultipartFile(
                "postImgae2",
                "test.txt",
                "text/plain",
                "test file".getBytes()
        );

        given(postFacade.addBasicPost(1L, validBasicPost, List.of(multipartFile, multipartFile2)))
                .willReturn(new SuccessResponse<>());

        mockMvc.perform(
                        multipart(HttpMethod.POST, ADD_BASIC_POST_REQUEST_URL)
                                .file(multipartFile)
                                .file(multipartFile2)
                                .content(objectMapper.writeValueAsString(validBasicPost))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(ResponseEnum.SUCCESS.isSuccess()))
                .andExpect(jsonPath("$.code").value(ResponseEnum.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResponseEnum.SUCCESS.getMessage()));
    }
}
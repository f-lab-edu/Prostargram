package flab.project.facade;

import flab.project.common.FileStorage.FileStorage;
import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.data.enums.FileType;
import flab.project.service.PostHashTagService;
import flab.project.service.PostImageService;
import flab.project.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class BasicPostFacadeTest {

    @InjectMocks
    private BasicPostFacade basicPostFacade;
    @Mock
    PostService postService;
    @Mock
    PostHashTagService postHashTagService;
    @Mock
    FileStorage fileStorage;
    @Mock
    PostImageService postImageService;

    @DisplayName("일반 게시물을 작성할 수 있다.")
    @Test
    void addPost() {
        // given
        long userId = 1L;
        List<MultipartFile> multiPartFiles = List.of((MultipartFile) createMockMultiPartFile(), (MultipartFile) createMockMultiPartFile());
        AddBasicPostRequest validBasicPostRequest = AddBasicPostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .contentImages(multiPartFiles)
                .build();

        // when
        basicPostFacade.addPost(userId, validBasicPostRequest);

        // then
        then(postService).should().addPost(userId, validBasicPostRequest);
        then(postHashTagService).should().saveAll(anyLong(), eq(validBasicPostRequest.getHashTagNames()));
        then(fileStorage).should().uploadFiles(userId, validBasicPostRequest.getContentImages(), FileType.POST_IMAGE);
        then(postImageService).should().saveAll(anyLong(), anyList());
    }

    @DisplayName("AddPollPostRequest가 아닌 다른 AddPostReuqest의 자식이 매개변수로 들어올 경우 RuntimeException을 던진다.")
    @Test
    void addPost_anotherChildOfAddPostRequest() {
        long userId = 1L;
        AddDebatePostRequest debatePostRequest = AddDebatePostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("content1", "content2"))
                .build();

        assertThatThrownBy(() -> basicPostFacade.addPost(userId, debatePostRequest))
                .isInstanceOf(RuntimeException.class);
    }

    private static MockMultipartFile createMockMultiPartFile() {
        return new MockMultipartFile(
                "contentImages",
                "test.jpg",
                "text/plain",
                "test file".getBytes()
        );
    }
}
package flab.project.facade;

import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.post.model.AddDebatePostRequest;
import flab.project.domain.post.model.AddPollPostRequest;
import flab.project.domain.post.facade.DebatePostFacade;
import flab.project.domain.post.service.DebatePostOptionService;
import flab.project.domain.post.service.PostHashTagService;
import flab.project.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class DebatePostFacadeTest {

    @InjectMocks
    private DebatePostFacade debatePostFacade;
    @Mock
    PostService postService;
    @Mock
    PostHashTagService postHashTagService;
    @Mock
    DebatePostOptionService debatePostOptionService;

    @DisplayName("DebatePost를 생성할 수 있다.")
    @Test
    void addPost() {
        long userId = 1L;
        AddDebatePostRequest validDebatePostRequest = AddDebatePostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("content1", "content2"))
                .build();

        debatePostFacade.addPost(userId, validDebatePostRequest);
        then(postService).should().addPost(userId, validDebatePostRequest);
        then(postHashTagService).should().saveAll(anyLong(), eq(validDebatePostRequest.getHashTagNames()));
        then(debatePostOptionService).should().savePostOptions(anyLong(), eq(validDebatePostRequest.getOptionContents()));
    }

    @DisplayName("AddPollPostRequest가 매개변수로 들어올 경우 RuntimeException을 던진다.")
    @Test
    void addPost_withAddPollPostRequest() {
        long userId = 1L;
        AddPollPostRequest pollPostRequest = AddPollPostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("content1", "content2"))
                .subject("test subject")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .allowMultipleVotes(true)
                .build();

        assertThatThrownBy(() -> debatePostFacade.addPost(userId, pollPostRequest))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("AddBasicPostRequest가 매개변수로 들어올 경우 RuntimeException을 던진다.")
    @Test
    void addPost_withAddBasicPostRequest() {
        long userId = 1L;
        List<MultipartFile> multiPartFiles = List.of((MultipartFile) createMockMultiPartFile(), (MultipartFile) createMockMultiPartFile());
        AddBasicPostRequest basicPostRequest = AddBasicPostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .contentImages(multiPartFiles)
                .build();

        assertThatThrownBy(() -> debatePostFacade.addPost(userId, basicPostRequest))
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
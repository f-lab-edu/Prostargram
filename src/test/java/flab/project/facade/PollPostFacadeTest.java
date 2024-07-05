package flab.project.facade;

import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.post.model.AddDebatePostRequest;
import flab.project.domain.post.model.AddPollPostRequest;
import flab.project.domain.post.facade.PollPostFacade;
import flab.project.domain.post.service.PollMetadataService;
import flab.project.domain.post.service.PollPostOptionService;
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
class PollPostFacadeTest {

    @InjectMocks
    private PollPostFacade pollPostFacade;
    @Mock
    PostService postService;
    @Mock
    PostHashTagService postHashTagService;
    @Mock
    PollMetadataService pollMetadataService;
    @Mock
    PollPostOptionService pollPostOptionService;

    @DisplayName("통계 게시물을 작성할 수 있다.")
    @Test
    void addPost() {
        // given
        long userId = 1L;
        AddPollPostRequest validPollPostRequest = AddPollPostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("content1", "content2"))
                .subject("test subject")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .allowMultipleVotes(true)
                .build();

        // when
        pollPostFacade.addPost(userId, validPollPostRequest);

        // then
        then(postService).should().addPost(userId, validPollPostRequest);
        then(postHashTagService).should().saveAll(anyLong(), eq(validPollPostRequest.getHashTagNames()));
        then(pollMetadataService).should().addMetadata(validPollPostRequest);
        then(pollPostOptionService).should().savePostOptions(anyLong(), eq(validPollPostRequest.getOptionContents()));
    }

    @DisplayName("AddDebatePostRequest가 매개변수로 들어올 경우 RuntimeException을 던진다.")
    @Test
    void addPost_withAddDebatePostRequest() {
        long userId = 1L;
        AddDebatePostRequest debatePostRequest = AddDebatePostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("content1", "content2"))
                .build();

        assertThatThrownBy(() -> pollPostFacade.addPost(userId, debatePostRequest))
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

        assertThatThrownBy(() -> pollPostFacade.addPost(userId, basicPostRequest))
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
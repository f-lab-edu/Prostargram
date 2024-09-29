package flab.project.facade;

import flab.project.common.fileStorage.FileStorage;
import flab.project.common.fileStorage.UploadedFileUrl;
import flab.project.common.fileStorage.UploadedFileUrls;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.post.model.AddDebatePostRequest;
import flab.project.domain.post.model.AddPollPostRequest;
import flab.project.domain.post.facade.BasicPostFacade;
import flab.project.domain.post.service.PostHashTagService;
import flab.project.domain.post.service.PostImageService;
import flab.project.domain.post.service.PostService;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static flab.project.domain.file.enums.FileType.POST_IMAGE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
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
    @Mock
    FanOutService fanOutService;

    @DisplayName("일반 게시물을 작성할 수 있다.")
    @Test
    void addPost() throws MalformedURLException {
        // given
        int imageCount = 1;
        long userId = 1L;
        AddBasicPostRequest validBasicPostRequest = AddBasicPostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .imageCount(imageCount)
                .build();

        URL url = new URL("http", "host.domain", "file/path");
        UploadedFileUrl uploadedFileUrl = new UploadedFileUrl(url);
        UploadedFileUrls uploadedFileUrls = new UploadedFileUrls(Set.of(uploadedFileUrl));
        given(fileStorage.generatePreSignedUrls(userId, imageCount, POST_IMAGE)).willReturn(uploadedFileUrls);

        // when
        basicPostFacade.addPost(userId, validBasicPostRequest);

        // then
        then(postService).should().addPost(userId, validBasicPostRequest);
        then(postHashTagService).should().saveAll(anyLong(), eq(validBasicPostRequest.getHashTagNames()));
        then(fanOutService).should().fanOut(userId, validBasicPostRequest.getPostId());
        then(fileStorage).should().generatePreSignedUrls(userId, imageCount, POST_IMAGE);
        then(postImageService).should().saveAll(anyLong(), anySet());
    }

    @DisplayName("AddDebatePostRequest 객체가 매개변수로 들어올 경우 RuntimeException을 던진다.")
    @Test
    void addPost_withAddDebatePostRequest() {
        long userId = 1L;
        AddDebatePostRequest debatePostRequest = AddDebatePostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("content1", "content2"))
                .build();

        assertThatThrownBy(() -> basicPostFacade.addPost(userId, debatePostRequest))
                .isInstanceOf(RuntimeException.class);
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

        assertThatThrownBy(() -> basicPostFacade.addPost(userId, pollPostRequest))
                .isInstanceOf(RuntimeException.class);
    }
}
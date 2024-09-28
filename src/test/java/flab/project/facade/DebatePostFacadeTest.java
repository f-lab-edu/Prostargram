package flab.project.facade;

import flab.project.domain.feed.service.FanOutService;
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

import java.time.LocalDate;
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
    @Mock
    FanOutService fanOutService;

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
        then(fanOutService).should().fanOut(userId,validDebatePostRequest.getPostId());
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
        int imageCount = 1;
        long userId = 1L;

        AddBasicPostRequest basicPostRequest = AddBasicPostRequest.builder()
                .content("게시물 내용입니다")
                .hashTagNames(Set.of("#test1", "#test2"))
                .imageCount(imageCount)
                .build();

        assertThatThrownBy(() -> debatePostFacade.addPost(userId, basicPostRequest))
                .isInstanceOf(RuntimeException.class);
    }
}
package flab.project.facade;

import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.data.dto.model.AddPollPostRequest;
import flab.project.service.PostHashTagService;
import flab.project.service.PostOptionService;
import flab.project.service.PostService;
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
    PostOptionService postOptionService;

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
        then(postOptionService).should().savePostOptions(anyLong(), eq(validDebatePostRequest.getOptionContents()));
    }

    @DisplayName("AddDebatePostRequest가 아닌 다른 AddPostReuqest의 자식이 매개변수로 들어올 경우 RuntimeException을 던진다.")
    @Test
    void addPost_anotherChildOfAddPostRequest() {
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
}
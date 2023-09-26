package flab.project.facade;

import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.data.dto.model.AddPollPostRequest;
import flab.project.service.*;
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
    PostOptionService postOptionService;

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
        then(postOptionService).should().savePostOptions(anyLong(), eq(validPollPostRequest.getOptionContents()));
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

        assertThatThrownBy(() -> pollPostFacade.addPost(userId, debatePostRequest))
                .isInstanceOf(RuntimeException.class);
    }
}
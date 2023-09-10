package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostMapper;
import flab.project.mapper.PostOptionsMapper;
import flab.project.mapper.VoteMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @InjectMocks
    VoteService voteService;
    @Mock
    VoteMapper voteMapper;
    @Mock
    PostOptionsMapper postOptionsMapper;
    @Mock
    PostMapper postMapper;

    @DisplayName("토론 게시물에 투표할 수 있다.")
    @Test
    void addDebatePostVote() {
        // given
        long postId = 1L;
        long optionId = 2L;
        long userId = 3L;

        // when
        voteService.addDebatePostVote(postId, optionId, userId);

        // then
        then(voteMapper).should().addPostVote(postId, Set.of(optionId), userId);
    }

    @DisplayName("통계 게시물에 투표할 수 있다.")
    @Test
    void addPollPostVote() {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 3L;

        given(postOptionsMapper.find(postId)).willReturn(optionIds);
        given(postMapper.check(postId)).willReturn(true);

        // when
        voteService.addPollPostVote(postId, optionIds, userId);

        // then
        then(voteMapper).should().addPostVote(postId, optionIds, userId);
    }

    @DisplayName("토론 게시물에 투표할 때, 토론 게시물의 optionId는 반드시 1 또는 2 값을 가진다.")
    @Test
    void addDebatePostVote_invalidRangeOfOptionId() {
        // given
        long postId = 1L;
        long invalidOptionId = 3L;
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addDebatePostVote(postId, invalidOptionId, userId)).isInstanceOf(InvalidUserInputException.class);
    }
}

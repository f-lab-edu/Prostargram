package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PollPostMapper;
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
    PollPostMapper pollPostMapper;

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

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(optionIds);
        given(pollPostMapper.checkAllowMultipleVotes(postId)).willReturn(true);

        // when
        voteService.addPollPostVote(postId, optionIds, userId);

        // then
        then(voteMapper).should().addPostVote(postId, optionIds, userId);
    }

    @DisplayName("토론 게시물에 투표할 때, postId와 userId는 양수여야 한다.")
    @Test
    void addDebatePostVote_invalidPostIdAndUserId() {
        // given
        long negativePostId = -1L;
        long optionId = 1L;
        long negativeUserId = -1L;

        // when & then
        assertThatThrownBy(() -> voteService.addDebatePostVote(negativePostId, optionId, negativeUserId)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, postId와 userId는 양수여야 한다.")
    @Test
    void addPollPostVote_invalidPostIdAndUserId() {
        // given
        long zeroPostId = 0L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long zeroUserId = 0L;

        // when & then
        assertThatThrownBy(() -> voteService.addPollPostVote(zeroPostId, optionIds, zeroUserId)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("토론 게시물에 투표할 때, optionId는 양수여야 한다.")
    @Test
    void addDebatePostVote_invalidOptionId() {
        // given
        long postId = 1L;
        long negativeOptionId = -1L;
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addDebatePostVote(postId, negativeOptionId, userId)).isInstanceOf(InvalidUserInputException.class);

        // given
        long zeroOptionId = 0L;

        // when & then
        assertThatThrownBy(() -> voteService.addDebatePostVote(postId, negativeOptionId, userId)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, optionId는 양수여야 한다.")
    @Test
    void addPollPostVote_invalidOptionIds() {
        // given
        long postId = 1L;
        Set<Long> invalidOptionIds = Set.of(0L, -1L, 1L);
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPollPostVote(postId, invalidOptionIds, userId)).isInstanceOf(InvalidUserInputException.class);
    }

    // Todo "통계 게시물에 투표할 때, 통계 게시물의 optionId는 반드시 1 ~ 5 값을 가진다."와 같은 로직은 Service에 추가해야하지 않나?
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

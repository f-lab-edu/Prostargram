package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.enums.PostType;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
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
        long optionId = 1L;
        long userId = 1L;

        // when
        voteService.addPostVote(postId, Set.of(optionId), userId, PostType.DEBATE);

        // then
        then(voteMapper).should().addPostVote(postId, Set.of(optionId), userId);
    }

    @DisplayName("통계 게시물에 투표할 수 있다.")
    @Test
    void addPollPostVote() {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 1L;

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(optionIds);
        given(pollPostMapper.findAllowMultipleVotes(postId)).willReturn(true);

        // when
        voteService.addPostVote(postId, optionIds, userId, PostType.POLL);

        // then
        then(voteMapper).should().addPostVote(postId, optionIds, userId);
    }

    @DisplayName("토론 게시물에 투표할 때, allowMultipleVotes는 항상 false이며 optionId는 하나의 값만 존재해야 한다.")
    @Test
    void validAllowMultipleVotes_validOptionId() {
        // given
        long postId = 1L;
        Set<Long> optionId = Set.of(1L);
        long userId = 1L;

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(optionId);
        given(pollPostMapper.findAllowMultipleVotes(postId)).willReturn(false);

        // when & then
        assertThatCode(() -> voteService.addPostVote(postId, optionId, userId, PostType.POLL))
                .doesNotThrowAnyException();
    }

    @DisplayName("통계 게시물에 투표할 때, allowMultipleVotes가 false일 경우 optionId는 하나의 값만 존재한다.")
    @Test
    void validAllowMultipleVotes_validOptionIds() {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L);
        long userId = 1L;

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(optionIds);
        given(pollPostMapper.findAllowMultipleVotes(postId)).willReturn(false);

        // when & then
        assertThatCode(() -> voteService.addPostVote(postId, optionIds, userId, PostType.POLL))
                .doesNotThrowAnyException();
    }

    @DisplayName("토론 게시물에 투표할 때, postId가 음수라면 InvalidUserInputException을 반환한다.")
    @Test
    void addDebatePostVote_invalidPostId() {
        // given
        long negativePostId = -1L;
        long optionId = 1L;
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(negativePostId, Set.of(optionId), userId, PostType.DEBATE)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("토론 게시물에 투표할 때, userId가 음수라면 InvalidUserInputException을 반환한다.")
    @Test
    void addDebatePostVote_invalidUserId() {
        // given
        long postId = -1L;
        long optionId = 1L;
        long negativeUserId = -1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, Set.of(optionId), negativeUserId, PostType.DEBATE)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, postId가 음수라면 InvalidUserInputException을 반환한다.")
    @Test
    void addPollPostVote_invalidPostId() {
        // given
        long zeroPostId = 0L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 0L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(zeroPostId, optionIds, userId, PostType.POLL)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, userId가 음수라면 InvalidUserInputException을 반환한다.")
    @Test
    void addPollPostVote_invalidUserId() {
        // given
        long postId = 0L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long zeroUserId = 0L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, optionIds, zeroUserId, PostType.POLL)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("토론 게시물에 투표할 때, 토론 게시물의 optionId는 반드시 1 또는 2 값을 가진다.")
    @Test
    void addDebatePostVote_invalidRangeOfOptionId() {
        // given
        long postId = 1L;
        long invalidOptionId = 3L;
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, Set.of(invalidOptionId), userId, PostType.DEBATE)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, optionId는 유효한 optionId들 중 하나여야 한다.")
    @Test
    void addPollPostVote_invalidOptionIds() {
        // given
        long postId = 1L;
        Set<Long> validOptionIds = Set.of(1L, 2L);
        long userId = 1L;

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(validOptionIds);

        Set<Long> invalidOptionIds = Set.of(3L);

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, invalidOptionIds, userId, PostType.POLL)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, allowMultipleVotes가 false일 경우 optionId가 두 개 이상이라면 InvalidUserInputException을 반환한다.")
    @Test
    void validAllowMultipleVotes_invalidOptionId() {
        // given
        long postId = 1L;
        Set<Long> multipleOptionIds = Set.of(1L, 2L);
        long userId = 1L;

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(Set.of(1L, 2L));
        given(pollPostMapper.findAllowMultipleVotes(postId)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, multipleOptionIds, userId, PostType.POLL)).isInstanceOf(InvalidUserInputException.class);
    }
}

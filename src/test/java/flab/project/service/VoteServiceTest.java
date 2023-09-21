package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.domain.PollPeriod;
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

import java.time.LocalDate;
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
        given(pollPostMapper.findPollPeriod(postId)).willReturn(new PollPeriod(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)));

        // when
        voteService.addPostVote(postId, optionIds, userId, PostType.POLL);

        // then
        then(voteMapper).should().addPostVote(postId, optionIds, userId);
    }

    @DisplayName("토론 게시물에 투표할 때, allowMultipleVotes는 항상 false이며 optionId는 하나의 값만 존재한다.")
    @Test
    void addDebatePostVote_validAllowMultipleVotes_validOptionId() {
        // given
        long postId = 1L;
        Set<Long> optionId = Set.of(1L);
        long userId = 1L;

        // when & then
        assertThatCode(() -> voteService.addPostVote(postId, optionId, userId, PostType.DEBATE))
                .doesNotThrowAnyException();
    }

    @DisplayName("통계 게시물에 투표할 때, allowMultipleVotes가 false일 경우 optionId는 하나의 값만 존재한다.")
    @Test
    void addPollPostVote_validAllowMultipleVotes_validOptionIds() {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L);
        long userId = 1L;

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(optionIds);
        given(pollPostMapper.findAllowMultipleVotes(postId)).willReturn(false);
        given(pollPostMapper.findPollPeriod(postId)).willReturn(new PollPeriod(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)));

        // when & then
        assertThatCode(() -> voteService.addPostVote(postId, optionIds, userId, PostType.POLL))
                .doesNotThrowAnyException();
    }

    @DisplayName("토론 게시물에 투표할 때, postId가 음수일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addDebatePostVote_invalidPostId() {
        // given
        long negativePostId = -1L;
        long optionId = 1L;
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(negativePostId, Set.of(optionId), userId, PostType.DEBATE)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("토론 게시물에 투표할 때, userId가 음수일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addDebatePostVote_invalidUserId() {
        // given
        long postId = 1L;
        long optionId = 1L;
        long negativeUserId = -1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, Set.of(optionId), negativeUserId, PostType.DEBATE)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, postId가 0일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addPollPostVote_invalidPostId() {
        // given
        long zeroPostId = 0L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(zeroPostId, optionIds, userId, PostType.POLL)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, userId가 0일 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addPollPostVote_invalidUserId() {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long zeroUserId = 0L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, optionIds, zeroUserId, PostType.POLL)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("토론 게시물에 투표할 때, 토론 게시물의 optionId가 1 또는 2 외의 값을 가질 경우 InvalidUserInputException을 반환한다.")
    @Test
    void addDebatePostVote_invalidRangeOfOptionId() {
        // given
        long postId = 1L;
        long invalidOptionId = 3L;
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, Set.of(invalidOptionId), userId, PostType.DEBATE)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, optionId는 유효한 optionId들 중 하나가 아닐 경우 InvalidUserInputException을 반환한다.")
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

    @DisplayName("통계 게시물에 투표할 때, allowMultipleVotes가 false일 경우 optionId가 한 개가 아니라면 InvalidUserInputException을 반환한다.")
    @Test
    void addPollPostVote_validAllowMultipleVotes_invalidOptionId() {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 1L;

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(optionIds);
        given(pollPostMapper.findAllowMultipleVotes(postId)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, optionIds, userId, PostType.POLL)).isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("통계 게시물에 투표할 때, 투표가 오픈하기 전이거나 마감된 경우 투표할 수 없다.")
    @Test
    void addPollPostVote_invalidEndDate() {
        // given
        long postId = 1L;
        Set<Long> optionIds = Set.of(1L, 2L);
        long userId = 1L;

        given(postOptionsMapper.findValidOptionIds(postId)).willReturn(optionIds);
        given(pollPostMapper.findAllowMultipleVotes(postId)).willReturn(true);
        given(pollPostMapper.findPollPeriod(postId)).willReturn(new PollPeriod(LocalDate.now().plusDays(1), LocalDate.now().minusDays(1)));

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postId, optionIds, userId, PostType.POLL)).isInstanceOf(InvalidUserInputException.class);
    }
}

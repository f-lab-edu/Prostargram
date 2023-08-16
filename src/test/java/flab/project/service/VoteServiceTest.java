package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.enums.PostType;
import flab.project.mapper.VoteMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @InjectMocks
    VoteService voteService;

    @Mock
    VoteMapper voteMapper;

    @DisplayName("토론 게시물에 투표할 때, 하나의 선택지만 제출했다면 정상적으로 투표가 반영된다.")
    @Test
    void validParameterForVoteInDebatePost() {
        // given
        PostType postType = PostType.DEBATE;
        List<Long> optionIds = List.of(1L);
        long userId = 1L;

        // when
        voteService.addPostVote(postType, optionIds, userId);

        // then
        then(voteMapper).should().addPostVote(optionIds.get(0), userId);
    }

    @DisplayName("토론 게시물에 투표할 때, 두 선택지를 모두 제출했다면 투표가 반영되지 않는다.")
    @Test
    void invalidParameterForVoteInDebatePost() {
        // given
        PostType postType = PostType.DEBATE;
        List<Long> invalidOptionIds = List.of(1L, 2L);
        long userId = 1L;

        // when & then
        assertThatThrownBy(() -> voteService.addPostVote(postType, invalidOptionIds, userId)).isInstanceOf(InvalidUserInputException.class);
    }
}

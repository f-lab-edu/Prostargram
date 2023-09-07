package flab.project.service;

import flab.project.mapper.VoteMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @InjectMocks
    VoteService voteService;
    @Mock
    VoteMapper voteMapper;

    @DisplayName("토론 게시물에 투표할 수 있다.")
    @Test
    void validParameterForVoteInDebatePost() {
        // given
        long postId = 1L;
        long optionId = 2L;
        long userId = 3L;

        given(voteMapper.find(postId)).willReturn(List.of(optionId));

        // when
        voteService.addDebatePostVote(postId, optionId, userId);

        // then
        then(voteMapper).should().addDebatePostVote(postId, optionId, userId);
    }

    @DisplayName("통계 게시물에 투표할 수 있다.")
    @Test
    void validParameterForVoteInPollPost() {
        // given
        long postId = 1L;
        List<Long> optionIds = List.of(1L, 2L);
        long userId = 3L;

        given(voteMapper.find(postId)).willReturn(optionIds);
        given(voteMapper.check(postId)).willReturn(true);

        // when
        voteService.addPollPostVote(postId, optionIds, userId);

        // then
        then(voteMapper).should().addPollPostVote(postId, optionIds, userId);
    }
}

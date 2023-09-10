package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostMapper;
import flab.project.mapper.PostOptionsMapper;
import flab.project.mapper.VoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteMapper voteMapper;
    private final PostOptionsMapper postOptionsMapper;
    private final PostMapper postMapper;

    // Todo 토론 게시물에서 postId가 유효한 Id인지 검증을 해야하지 않나?
    public SuccessResponse addDebatePostVote(long postId, long optionId, long userId) {
        Set<Long> validOptionIds = Set.of(1L, 2L);

        checkPostIdAndUserId(postId, Set.of(optionId), userId, validOptionIds);

        voteMapper.addPostVote(postId, Set.of(optionId), userId);

        return new SuccessResponse();
    }

    public SuccessResponse addPollPostVote(long postId, Set<Long> optionIds, long userId) {
        Set<Long> validOptionIds = postOptionsMapper.find(postId);

        checkPostIdAndUserId(postId, optionIds, userId, validOptionIds);
        checkMultipleVotes(postId, optionIds);

        voteMapper.addPostVote(postId, optionIds, userId);

        return new SuccessResponse();
    }

    private void checkPostIdAndUserId(long postId, Set<Long> optionIds, long userId, Set<Long> validOptionIds) {
        if (postId <= 0) {
            throw new InvalidUserInputException("Invalid postId.");
        }

        if (userId <= 0) {
            throw new InvalidUserInputException("Invalid userId.");
        }

        for (Long id : optionIds) {
            if (!validOptionIds.contains(id)) {
                throw new InvalidUserInputException("OptionIds should be one of the fixed values.");
            }
        }
    }

    private void checkMultipleVotes(long postId, Set<Long> optionIds) {
        boolean allowMultipleVotes = postMapper.check(postId);

        if (!allowMultipleVotes && optionIds.size() > 1) {
            throw new InvalidUserInputException("Multiple selections are not allowed for this poll post.");
        }
    }
}
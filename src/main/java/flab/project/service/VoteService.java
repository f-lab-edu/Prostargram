package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.enums.PostType;
import flab.project.mapper.PollPostMapper;
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
    private final PollPostMapper pollPostMapper;

    public SuccessResponse addDebatePostVote(long postId, long optionId, long userId) {
        validateVote(postId, Set.of(optionId), userId, PostType.DEBATE);

        voteMapper.addPostVote(postId, Set.of(optionId), userId);

        return new SuccessResponse();
    }

    public SuccessResponse addPollPostVote(long postId, Set<Long> optionIds, long userId) {
        validateVote(postId, optionIds, userId, PostType.POLL);

        voteMapper.addPostVote(postId, optionIds, userId);

        return new SuccessResponse();
    }

    private void validateVote(long postId, Set<Long> optionIds, long userId, PostType postType) {
        Set<Long> validOptionIds;

        if (postType == PostType.DEBATE) {
            validOptionIds = Set.of(1L, 2L);
        } else {
            validOptionIds = postOptionsMapper.findValidOptionIds(postId);
        }

        checkPostIdAndUserId(postId, userId);
        checkOptionIds(optionIds, validOptionIds);

        if (postType == PostType.POLL) {
            checkMultipleVotes(postId, optionIds);
        }
    }

    private void checkPostIdAndUserId(long postId, long userId) {
        if (postId <= 0) {
            throw new InvalidUserInputException("Invalid postId.");
        }

        if (userId <= 0) {
            throw new InvalidUserInputException("Invalid userId.");
        }
    }

    private void checkOptionIds(Set<Long> optionIds, Set<Long> validOptionIds) {
        for (Long id : optionIds) {
            if (!validOptionIds.contains(id)) {
                throw new InvalidUserInputException("OptionIds should be one of the fixed values.");
            }
        }
    }

    private void checkMultipleVotes(long postId, Set<Long> optionIds) {
        boolean allowMultipleVotes = pollPostMapper.checkAllowMultipleVotes(postId);

        if (!allowMultipleVotes && optionIds.size() > 1) {
            throw new InvalidUserInputException("Multiple selections are not allowed for this poll post.");
        }
    }
}
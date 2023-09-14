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

    public SuccessResponse addPostVote(long postId, Set<Long> optionIds, long userId, PostType postType) {
        validateVote(postId, optionIds, userId, postType);

        voteMapper.addPostVote(postId, optionIds, userId);

        return new SuccessResponse();
    }

    private void validateVote(long postId, Set<Long> optionIds, long userId, PostType postType) {
        validatePostIdAndUserId(postId, userId);
        validateOptionIds(postId, optionIds, postType);

        if (postType == PostType.POLL) {
            validateMultipleVotes(postId, optionIds);
        }
    }

    private void validatePostIdAndUserId(long postId, long userId) {
        if (postId <= 0) {
            throw new InvalidUserInputException("Invalid postId.");
        }

        if (userId <= 0) {
            throw new InvalidUserInputException("Invalid userId.");
        }
    }

    private void validateOptionIds(long postId, Set<Long> optionIds, PostType postType) {
        Set<Long> validOptionIds = getValidOptionIds(postId, postType);

        for (Long id : optionIds) {
            if (!validOptionIds.contains(id)) {
                throw new InvalidUserInputException("OptionIds should be one of the fixed values.");
            }
        }
    }

    private Set<Long> getValidOptionIds(long postId, PostType postType) {
        if (postType == PostType.DEBATE) {
            return Set.of(1L, 2L);
        } else {
            return postOptionsMapper.findValidOptionIds(postId);
        }
    }

    private void validateMultipleVotes(long postId, Set<Long> optionIds) {
        boolean allowMultipleVotes = pollPostMapper.findAllowMultipleVotes(postId);

        if (!allowMultipleVotes && optionIds.size() > 1) {
            throw new InvalidUserInputException("Multiple selections are not allowed for this poll post.");
        }
    }
}
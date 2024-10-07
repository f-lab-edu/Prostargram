package flab.project.domain.post.service;

import flab.project.cache.cacheManager.VoteCacheManager;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NotFoundException;
import flab.project.domain.post.model.PollPeriod;
import flab.project.domain.post.enums.PostType;
import flab.project.domain.post.mapper.PollMetadataMapper;
import flab.project.domain.post.mapper.PostOptionsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class VoteService {

    private static final Set<Long> debatePostOptionIds = Set.of(1L, 2L);

    private final VoteCacheManager voteCacheManager;
    private final PostOptionsMapper postOptionsMapper;
    private final PollMetadataMapper pollMetadataMapper;

    public SuccessResponse<Void> addPostVote(
            long postId,
            Set<Long> optionIds,
            long userId,
            PostType postType
    ) {
        if (postType == null) {
            throw new NotFoundException("post not found.");
        }

        validateVote(postId, optionIds, userId, postType);

        voteCacheManager.addPostVote(postId, userId, optionIds);

        return new SuccessResponse<>();
    }

    private void validateVote(long postId, Set<Long> optionIds, long userId, PostType postType) {
        validatePostIdAndUserId(postId, userId);
        validateOptionIds(postId, optionIds, postType);
        validateMultipleVotes(postId, optionIds, postType);

        if (postType == PostType.POLL) {
            validatePollPostPeriod(postId);
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

        optionIds.stream().filter(i -> !validOptionIds.contains(i)).findAny().ifPresent(i -> {
            throw new InvalidUserInputException(
                    String.format("Invalid optionId %d is received", i));
        });
    }

    private Set<Long> getValidOptionIds(long postId, PostType postType) {
        if (postType == PostType.DEBATE) {
            return debatePostOptionIds;
        } else {
            return postOptionsMapper.findValidOptionIds(postId);
        }
    }

    private void validateMultipleVotes(long postId, Set<Long> optionIds, PostType postType) {
        boolean allowMultipleVotes = getAllowMultipleVotes(postId, postType);

        if (!allowMultipleVotes && optionIds.size() > 1) {
            throw new InvalidUserInputException(
                    "Multiple selections are not allowed for this poll post.");
        }
    }

    private boolean getAllowMultipleVotes(long postId, PostType postType) {
        if(postType != PostType.POLL){
            return false;
        }

        return pollMetadataMapper.findAllowMultipleVotes(postId);
    }

    private void validatePollPostPeriod(long postId) {
        PollPeriod pollPeriod = pollMetadataMapper.findPollPeriod(postId);

        LocalDate startDate = pollPeriod.getStartDate();
        LocalDate endDate = pollPeriod.getEndDate();
        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) {
            throw new InvalidUserInputException("This poll post has not started yet.");
        } else if (endDate.isBefore(today)) {
            throw new InvalidUserInputException("This poll post has expired.");
        }
    }
}
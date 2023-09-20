package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.enums.PostType;
import flab.project.mapper.PollPostMapper;
import flab.project.mapper.PostOptionsMapper;
import flab.project.mapper.VoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteMapper voteMapper;
    private final PostOptionsMapper postOptionsMapper;
    private final PollPostMapper pollPostMapper;

    private static final Set<Long> debatePostOptionIds = Set.of(1L, 2L);

    public SuccessResponse addPostVote(long postId, Set<Long> optionIds, long userId, PostType postType) {
        validateVote(postId, optionIds, userId, postType);

        voteMapper.addPostVote(postId, optionIds, userId);

        return new SuccessResponse();
    }

    private void validateVote(long postId, Set<Long> optionIds, long userId, PostType postType) {
        validatePostIdAndUserId(postId, userId);
        validateOptionIds(postId, optionIds, postType);
        validateMultipleVotes(postId, optionIds, postType);

        if (postType == PostType.POLL) {
            validatePollPostExpiration(postId);
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

        optionIds.stream()
                .filter(i -> !validOptionIds.contains(i))
                .findAny()
                .ifPresent(i -> {
                    throw new InvalidUserInputException(String.format("Invalid optionId %d is received", i));
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
            throw new InvalidUserInputException("Multiple selections are not allowed for this poll post.");
        }
    }

    private boolean getAllowMultipleVotes(long postId, PostType postType) {
        return postType == PostType.POLL ? pollPostMapper.findAllowMultipleVotes(postId) : false;
    }

    private void validatePollPostExpiration(long postId) {
        // Todo 투표의 유효기간(시작 날짜, 종료 날짜)을 각각 DB에 한 번씩 접근해서 따로 가져오는 건 비효율적인 것 같고, 이를 위해 Dto를 설계하긴 또 좀 애매한 것 같은데..
        LocalDate startDate = pollPostMapper.findStartDate(postId);
        LocalDate endDate = pollPostMapper.findEndDate(postId);
        LocalDate today = LocalDate.now();

        if (startDate.isAfter(today)) {
            throw new InvalidUserInputException("This poll post has not started yet.");
        } else if (endDate.isBefore(today)) {
            throw new InvalidUserInputException("This poll post has expired.");
        }
    }
}
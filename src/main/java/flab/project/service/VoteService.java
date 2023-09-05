package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.VoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteMapper voteMapper;

    public SuccessResponse addDebatePostVote(long postId, long optionId, long userId) {
        checkUserIdAndOptionId(postId, optionId, userId);

        voteMapper.addPostVote(postId, optionId, userId);

        return new SuccessResponse();
    }

    public SuccessResponse addPollPostVote(long postId, List<Long> optionIds, long userId) {
        List<Long> actualOptionIds = voteMapper.find(postId);
        boolean allowDuplicateCheck = voteMapper.check(postId);

        checkUserIdAndOptionIds(postId, optionIds, userId);

        if (!allowDuplicateCheck && optionIds.size() > 1) {
            throw new InvalidUserInputException("Multiple selections are not allowed for this poll.");
        }

        if (optionIds.size() > actualOptionIds.size()) {
            throw new InvalidUserInputException("Selected more options than available.");
        }

        for (Long selectedOptionId : optionIds) {
            if (!actualOptionIds.contains(selectedOptionId)) {
                throw new InvalidUserInputException("Invalid option ID selected.");
            }
        }

        for (Long optionId : optionIds) {
            voteMapper.addPostVote(postId, optionId, userId);
        }

        return new SuccessResponse();
    }

    private void checkUserIdAndOptionId(long postId, long optionId, long userId) {
        if (postId <= 0 || optionId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid postId or userId");
        }
    }

    private void checkUserIdAndOptionIds(long postId, List<Long> optionIds, long userId) {
        if (postId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid userId.");
        }

        for (Long id : optionIds) {
            if (id <= 0) {
                throw new InvalidUserInputException("All optionIds should be positive values.");
            }
        }
    }
}

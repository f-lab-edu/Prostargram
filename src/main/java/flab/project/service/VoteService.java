package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.VoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteMapper voteMapper;

    public SuccessResponse addDebatePostVote(long postId, long optionId, long userId) {
        checkPostIdAndOptionId(postId, optionId, userId);

        List<Long> validOptionIds = voteMapper.find(postId);

        if (!validOptionIds.contains(optionId)) {
            throw new InvalidUserInputException("Invalid option ID selected.");
        }

        voteMapper.addDebatePostVote(postId, optionId, userId);

        return new SuccessResponse();
    }

    @Transactional
    public SuccessResponse addPollPostVote(long postId, List<Long> optionIds, long userId) {
        List<Long> validOptionIds = voteMapper.find(postId);
        boolean duplicateCheck = voteMapper.check(postId);

        checkPostIdAndOptionIds(postId, optionIds, userId);

        if (!duplicateCheck && optionIds.size() > 1) {
            throw new InvalidUserInputException("Multiple selections are not allowed for this poll.");
        }

        if (optionIds.size() > validOptionIds.size()) {
            throw new InvalidUserInputException("Selected more options than available.");
        }

        for (Long selectOptionId : optionIds) {
            if (!validOptionIds.contains(selectOptionId)) {
                throw new InvalidUserInputException("Invalid option ID selected.");
            }
        }

        voteMapper.addPollPostVote(postId, optionIds, userId);

        return new SuccessResponse();
    }

    // Todo userId는 추후, JWT 구현 후 삭제할 예정이므로 메서드 이름에서 userId를 제외한채 명명
    private void checkPostIdAndOptionId(long postId, long optionId, long userId) {
        if (postId <= 0 || optionId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid postId or userId");
        }
    }

    private void checkPostIdAndOptionIds(long postId, List<Long> optionIds, long userId) {
        if (postId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid userId.");
        }

        for (Long id : optionIds) {
            if (id == null || id <= 0) {
                throw new InvalidUserInputException("All optionIds should be positive values.");
            }
        }
    }
}
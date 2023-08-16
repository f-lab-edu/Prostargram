package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.enums.PostType;
import flab.project.mapper.VoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoteService {

    private final VoteMapper voteMapper;

    public SuccessResponse addPostVote(PostType postType, List<Long> optionIds, long userId) {
        int optionCount = optionIds.size();

        switch (postType) {
            case DEBATE:
                if (optionCount != 1) {
                    throw new InvalidUserInputException("Only one option should be selected for Debate Post.");
                }
                voteMapper.addPostVote(optionIds.get(0), userId);
                break;

            case POLL:
                if (optionCount < 2 || optionCount > 5) {
                    throw new InvalidUserInputException("Between 2 to 5 options should be selected for Poll Post.");
                }
                for (Long optionId : optionIds) {
                    voteMapper.addPostVote(optionId, userId);
                }
                break;

            default:
                throw new InvalidUserInputException("Invalid post type.");
        }
        return new SuccessResponse();
    }
}

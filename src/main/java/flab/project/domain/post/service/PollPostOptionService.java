package flab.project.domain.post.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.post.mapper.PostOptionMapper;
import flab.project.domain.post.template.PostOptionServiceTemplate;
import org.springframework.stereotype.Service;

import static flab.project.common.Constraints.*;

@Service
public class PollPostOptionService extends PostOptionServiceTemplate {

    public PollPostOptionService(PostOptionMapper postOptionMapper) {
        super(postOptionMapper);
    }

    @Override
    protected int getMaxLengthOfOptionContent() {
        return MAX_LENGTH_POLL_POST_OPTION_CONTENT;
    }

    @Override
    protected void validateOptionContentsSize(int optionContentsCount) {
        validateIsLessThanMinCountOfOptionCount(optionContentsCount);
        validateIsMoreThanMaxCountOfOptionCount(optionContentsCount);
    }

    private void validateIsLessThanMinCountOfOptionCount(int optionContentsCount) {
        if (optionContentsCount < MIN_SIZE_OF_POLL_POST_OPTION_COUNT) {
            throw new InvalidUserInputException();
        }
    }

    private void validateIsMoreThanMaxCountOfOptionCount(int optionContentsCount) {
        if (optionContentsCount > MAX_SIZE_OF_POLL_POST_OPTION_COUNT) {
            throw new InvalidUserInputException();
        }
    }
}
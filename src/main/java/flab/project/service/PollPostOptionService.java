package flab.project.service;

import static flab.project.common.Constraints.MAX_COUNT_OF_POLL_POST_OPTION_COUNT;
import static flab.project.common.Constraints.MAX_LENGTH_POLL_POST_OPTION_CONTENT;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostOptionMapper;
import flab.project.template.PostOptionServiceTemplate;
import org.springframework.stereotype.Service;

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
    protected void validateOptionContentsCount(int optionContentsCount) {
        if (optionContentsCount > MAX_COUNT_OF_POLL_POST_OPTION_COUNT) {
            throw new InvalidUserInputException();
        }
    }
}

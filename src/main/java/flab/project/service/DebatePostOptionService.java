package flab.project.service;

import static flab.project.common.Constraints.MAX_LENGTH_DEBATE_POST_OPTION_CONTENT;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostOptionMapper;
import flab.project.template.PostOptionServiceTemplate;
import org.springframework.stereotype.Service;

@Service
public class DebatePostOptionService extends PostOptionServiceTemplate {

    public DebatePostOptionService(PostOptionMapper postOptionMapper) {
        super(postOptionMapper);
    }

    @Override
    protected int getMaxLengthOfOptionContent() {
        return MAX_LENGTH_DEBATE_POST_OPTION_CONTENT;
    }

    @Override
    protected void validateOptionContentsCount(int optionContentsCount) {
        if (optionContentsCount > MAX_LENGTH_DEBATE_POST_OPTION_CONTENT) {
            throw new InvalidUserInputException();
        }
    }
}
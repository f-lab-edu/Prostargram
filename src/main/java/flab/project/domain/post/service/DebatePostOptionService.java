package flab.project.domain.post.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.post.mapper.PostOptionMapper;
import flab.project.domain.post.template.PostOptionServiceTemplate;
import org.springframework.stereotype.Service;

import static flab.project.common.Constraints.FIXED_SIZE_OF_DEBATE_POST_OPTIONS;
import static flab.project.common.Constraints.MAX_LENGTH_DEBATE_POST_OPTION_CONTENT;

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
    protected void validateOptionContentsSize(int optionContentsCount) {
        if (optionContentsCount != FIXED_SIZE_OF_DEBATE_POST_OPTIONS) {
            throw new InvalidUserInputException();
        }
    }
}
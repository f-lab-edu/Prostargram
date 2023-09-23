package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostOptionMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static flab.project.common.Constraints.*;

@RequiredArgsConstructor
@Service
public class PostOptionService {

    private final PostOptionMapper postOptionMapper;

    public void saveDebatePostOptions(long postId, List<String> optionContents) {
        validateSaveDebatePostOptions(postId, optionContents);

        savePostOptions(postId, optionContents);
    }

    public void savePollPostOptions(long postId, List<String> optionContents) {
        validatePollPostOptions(postId, optionContents);

        savePostOptions(postId, optionContents);
    }

    private void savePostOptions(long postId, List<String> optionContents) {
        int numberOfAffectedRow = postOptionMapper.saveAll(postId, optionContents);

        if (numberOfAffectedRow != optionContents.size()) {
            throw new RuntimeException();
        }
    }

    private void validateSaveDebatePostOptions(long postId, List<String> optionContents) {
        validatePostId(postId);

        validateDebateOptionContentCount(optionContents);

        validateOptionContent(optionContents, MAX_LENGTH_DEBATE_POST_OPTION_CONTENT);
    }

    private void validatePollPostOptions(long postId, List<String> optionContents) {
        validatePostId(postId);

        validatePollOptionContentCount(optionContents);

        validateOptionContent(optionContents, MAX_LENGTH_POLL_POST_OPTION_CONTENT);
    }

    private void validateOptionContent(List<String> optionContents, int contentMaxLength) {
        boolean isInvalidOptionContent = optionContents.stream()
                .anyMatch(optionContent -> optionContent.length() > contentMaxLength || StringUtils.isBlank(optionContent));

        if (isInvalidOptionContent) {
            throw new InvalidUserInputException();
        }
    }

    private void validateDebateOptionContentCount(List<String> optionContents) {
        if (optionContents.size() != COUNT_OF_DEBATE_POST_OPTION_COUNT) {
            throw new InvalidUserInputException();
        }
    }

    private void validatePollOptionContentCount(List<String> optionContents) {
        int optionContentSize = optionContents.size();

        if (optionContentSize < MIN_COUNT_OF_POLL_POST_OPTION_COUNT || optionContentSize > MAX_COUNT_OF_POLL_POST_OPTION_COUNT) {
            throw new InvalidUserInputException();
        }
    }

    private void validatePostId(long postId) {
        if (postId < 0) {
            throw new InvalidUserInputException();
        }
    }
}
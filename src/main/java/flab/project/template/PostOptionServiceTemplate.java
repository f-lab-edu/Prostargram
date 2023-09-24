package flab.project.template;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostOptionMapper;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class PostOptionServiceTemplate {

    private final PostOptionMapper postOptionMapper;
    
    public void savePostOptions(long postId, Set<String> optionContents) {
        validateSavePostOptions(postId, optionContents);

        int numberOfAffectedRow = postOptionMapper.saveAll(postId, optionContents);

        if (numberOfAffectedRow != optionContents.size()) {
            throw new RuntimeException();
        }
    }

    private void validateSavePostOptions(long postId, Set<String> optionContents) {
        validatePostId(postId);

        validateOptionContents(optionContents);

        validateOptionContentsCount(optionContents.size());
    }


    private void validateOptionContents(Set<String> optionContents) {
        int contentMaxLength = getMaxLengthOfOptionContent();

        boolean isInvalidOptionContent = optionContents.stream()
                .anyMatch(optionContent -> optionContent.length() > contentMaxLength || StringUtils.isBlank(optionContent));

        if (isInvalidOptionContent) {
            throw new InvalidUserInputException();
        }
    }

    private void validatePostId(long postId) {
        if (postId < 0) {
            throw new InvalidUserInputException();
        }
    }

    protected abstract int getMaxLengthOfOptionContent();
    protected abstract void validateOptionContentsCount(int optionContentsCount);
}
package flab.project.domain.post.template;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.model.Option;
import flab.project.domain.post.mapper.PostOptionMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public abstract class PostOptionServiceTemplate {
    private final PostOptionMapper postOptionMapper;

    public Set<Option> savePostOptions(long postId, Set<String> optionContents) {
        validateSavePostOptions(postId, optionContents);

        postOptionMapper.saveAll(postId, optionContents);

        return postOptionMapper.findAll(postId);
    }

    private void validateSavePostOptions(long postId, Set<String> optionContents) {
        validatePostIdPositive(postId);

        validateOptionContentsBlank(optionContents);
        validateOptionContentsLength(optionContents);
        validateOptionContentsSize(optionContents.size());
    }

    private void validatePostIdPositive(long postId) {
        if (postId <= 0) {
            throw new InvalidUserInputException();
        }
    }

    private void validateOptionContentsBlank(Set<String> optionContents) {
        boolean hasBlankOptionContent = optionContents.stream()
                .anyMatch(StringUtils::isBlank);

        if (hasBlankOptionContent) {
            throw new InvalidUserInputException();
        }
    }

    private void validateOptionContentsLength(Set<String> optionContents) {
        int contentMaxLength = getMaxLengthOfOptionContent();

        boolean isInvalidOptionContent = optionContents.stream()
                .anyMatch(optionContent -> optionContent.length() > contentMaxLength);

        if (isInvalidOptionContent) {
            throw new InvalidUserInputException();
        }
    }

    protected abstract int getMaxLengthOfOptionContent();

    protected abstract void validateOptionContentsSize(int optionContentsSize);
}

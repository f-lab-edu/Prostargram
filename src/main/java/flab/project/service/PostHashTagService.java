package flab.project.service;

import static flab.project.data.dto.model.HashTag.LIMIT_OF_MAX_LENGTH;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfPostHashTagExceededException;
import flab.project.mapper.HashTagMapper;
import flab.project.mapper.PostHashTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PostHashTagService {

    private static final int POST_HASHTAG_MAX_LENGTH = 5;

    private final HashTagMapper hashTagMapper;
    private final PostHashTagMapper postHashTagMapper;
    private final HashTagService hashTagService;

    public void saveAll(long postId, Set<String> hashTagNames) {

        validateSavePostHashTags(postId, hashTagNames);

        List<Long> hashTagIds = hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        int NumberOfAffectedRow = postHashTagMapper.saveAll(postId, hashTagIds);

        if (NumberOfAffectedRow != hashTagIds.size()) {
            throw new RuntimeException();
        }
    }

    private void validateSavePostHashTags(long postId, Set<String> hashTagNames) {
        validatePostId(postId);

        validateNumberLimitOfPostHashTags(hashTagNames);

        validateMaxLengthOfHashTags(hashTagNames);
    }

    private static void validateMaxLengthOfHashTags(Set<String> hashTagNames) {
        boolean isExceedMaxLength = hashTagNames.stream()
                .anyMatch(hashTagName -> hashTagName.length() > LIMIT_OF_MAX_LENGTH);

        if (isExceedMaxLength) {
            throw new InvalidUserInputException();
        }
    }

    private static void validateNumberLimitOfPostHashTags(Set<String> hashTagNames) {
        if (hashTagNames.size() > POST_HASHTAG_MAX_LENGTH) {
            throw new NumberLimitOfPostHashTagExceededException();
        }
    }

    private void validatePostId(long postId) {
        if (postId < 0) {
            throw new InvalidUserInputException();
        }
    }
}
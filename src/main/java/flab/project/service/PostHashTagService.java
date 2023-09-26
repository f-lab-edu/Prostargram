package flab.project.service;

import static flab.project.common.Constraints.MAX_LENGTH_OF_HASHTAGS;
import static flab.project.common.Constraints.MAX_SIZE_OF_POST_HASHTAGS;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostHashTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class PostHashTagService {

    private final PostHashTagMapper postHashTagMapper;
    private final HashTagService hashTagService;

    public void saveAll(long postId, Set<String> hashTagNames) {
        validateSavePostHashTags(postId, hashTagNames);

        // TODO findHashTagIdsByHashTagNames는 내부에서 존재하지 않는 HashTagNames는 해시태그 테이블에 저장해주는 역할까지 하고있다.
        // TODO 이게 괜찮은걸까..?
        Set<Long> hashTagIds = hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        int numberOfAffectedRow = postHashTagMapper.saveAll(postId, hashTagIds);

        if (numberOfAffectedRow != hashTagIds.size()) {
            throw new RuntimeException();
        }
    }

    private void validateSavePostHashTags(long postId, Set<String> hashTagNames) {
        validatePostId(postId);

        validateLimitOfPostHashTagsSize(hashTagNames);

        validateMaxLengthOfHashTags(hashTagNames);
    }

    private void validatePostId(long postId) {
        if (postId < 0) {
            throw new InvalidUserInputException();
        }
    }

    private static void validateLimitOfPostHashTagsSize(Set<String> hashTagNames) {
        if (hashTagNames.size() > MAX_SIZE_OF_POST_HASHTAGS) {
            throw new InvalidUserInputException();
        }
    }

    private static void validateMaxLengthOfHashTags(Set<String> hashTagNames) {
        boolean isExceedMaxLength = hashTagNames.stream()
                .anyMatch(hashTagName -> hashTagName.length() > MAX_LENGTH_OF_HASHTAGS);

        if (isExceedMaxLength) {
            throw new InvalidUserInputException();
        }
    }
}
package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostImageMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Set;


@RequiredArgsConstructor
@Service
public class PostImageService {

    private final PostImageMapper postImageMapper;

    public void saveAll(long postId, Set<String> uploadedFileUrls) {
        validateSaveAll(postId, uploadedFileUrls);
        postImageMapper.saveAll(postId, uploadedFileUrls);
    }

    private void validateSaveAll(long postId, Set<String> uploadedFileUrls) {
        validatePostIdPositive(postId);
        validateUploadFileUrlsEmpty(uploadedFileUrls);
        validateUploadFileUrlsNotBlank(uploadedFileUrls);
    }

    private void validatePostIdPositive(long postId) {
        if (postId <= 0) {
            throw new InvalidUserInputException();
        }
    }

    private void validateUploadFileUrlsEmpty(Set<String> uploadedFileUrls) {
        if (ObjectUtils.isEmpty(uploadedFileUrls)) {
            throw new InvalidUserInputException();
        }
    }

    private void validateUploadFileUrlsNotBlank(Set<String> uploadedFileUrls) {
        boolean hasBlank = uploadedFileUrls.stream()
                .anyMatch(StringUtils::isBlank);

        if (hasBlank) {
            throw new InvalidUserInputException();
        }
    }
}
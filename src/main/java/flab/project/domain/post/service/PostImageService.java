package flab.project.domain.post.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.post.mapper.PostImageMapper;
import java.util.stream.Collectors;
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

    public void update(long postId, Set<String> newContentImageUrls) {
        Set<String> currentContentImageUrls = postImageMapper.findAllByPostId(postId);

        Set<String> shouldRemoveImageUrls = currentContentImageUrls.stream()
                .filter(url -> !newContentImageUrls.contains(url))
                .collect(Collectors.toSet());

        if (!shouldRemoveImageUrls.isEmpty()) {
            postImageMapper.removeAll(postId, shouldRemoveImageUrls);
        }

        Set<String> shouldSaveImageUrls = newContentImageUrls.stream()
                .filter(url -> !currentContentImageUrls.contains(url))
                .collect(Collectors.toSet());

        if (!shouldSaveImageUrls.isEmpty()) {
            postImageMapper.saveAll(postId, shouldSaveImageUrls);
        }
    }
}
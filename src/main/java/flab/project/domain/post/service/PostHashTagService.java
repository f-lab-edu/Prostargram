package flab.project.domain.post.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.post.mapper.PostHashTagMapper;
import flab.project.domain.post.model.UpdateBasicPostRequest;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class PostHashTagService {

    private final PostHashTagMapper postHashTagMapper;
    private final HashTagService hashTagService;

    public void saveAll(long postId, Set<String> hashTagNames) {
        validatePostIdPositive(postId);

        if (ObjectUtils.isEmpty(hashTagNames)) {
            return;
        }

        Set<Long> hashTagIds = hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        postHashTagMapper.saveAll(postId, hashTagIds);
    }

    private void validatePostIdPositive(long postId) {
        if (postId <= 0) {
            throw new InvalidUserInputException();
        }
    }

    public void update(long postId, Set<String> newPostHashTagNames) {
        Set<Long> currentHashTagIds = postHashTagMapper.findAllByPostId(postId);
        Set<Long> newHashTagIds = hashTagService.findHashTagIdsByHashTagNames(newPostHashTagNames);

        Set<Long> shouldRemoveHashTagIds = currentHashTagIds.stream()
                .filter(id -> !newHashTagIds.contains(id))
                .collect(Collectors.toSet());

        if (!shouldRemoveHashTagIds.isEmpty()) {
            postHashTagMapper.removeAll(postId, shouldRemoveHashTagIds);
        }

        Set<Long> shouldSaveHashTagIds = newHashTagIds.stream()
                .filter(id -> !currentHashTagIds.contains(id))
                .collect(Collectors.toSet());

        if (!shouldSaveHashTagIds.isEmpty()) {
            postHashTagMapper.saveAll(postId, shouldSaveHashTagIds);
        }

    }
}
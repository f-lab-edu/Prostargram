package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostHashTagMapper;
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
}
package flab.project.service;

import static flab.project.common.Constraints.MAX_LENGTH_OF_HASHTAGS;
import static flab.project.common.Constraints.MAX_SIZE_OF_POST_HASHTAGS;

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
        // TODO findHashTagIdsByHashTagNames는 내부에서 존재하지 않는 HashTagNames는 해시태그 테이블에 저장해주는 역할까지 하고있다.
        // TODO 이게 괜찮은걸까..?
        if (ObjectUtils.isEmpty(hashTagNames)) {
            return;
        }

        Set<Long> hashTagIds = hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        int numberOfAffectedRow = postHashTagMapper.saveAll(postId, hashTagIds);

        if (numberOfAffectedRow != hashTagIds.size()) {
            throw new RuntimeException();
        }
    }
}
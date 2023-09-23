package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.mapper.HashTagMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HashTagService {

    private final HashTagMapper hashTagMapper;

    public Long getHashTagIdByHashtagName(String interestName) {
        return hashTagMapper.getHashtagIdByHashtagName(interestName);
    }

    public long addHashTag(HashTag hashTag) {
        hashTagMapper.save(hashTag);

        return hashTag.getHashTagId();
    }

    public List<Long> findHashTagIdsByHashTagNames(List<String> hashTagNames) {
        List<HashTag> existingHashTags = hashTagMapper.getHashTagsByHashtagNames(hashTagNames);

        List<HashTag> nonExistHashTags = findNonExistingHashTags(hashTagNames, existingHashTags);

        saveNonExistHashTagsAndSetHashTagIds(nonExistHashTags);

        return unionHashTagIds(existingHashTags, nonExistHashTags);
    }

    private List<HashTag> findNonExistingHashTags(List<String> hashTagNames, List<HashTag> existingHashTags) {
        Map<String, HashTag> existingHashMap = convertHashTagMap(existingHashTags);

        return hashTagNames.stream()
                .filter((hashTagName) -> !existingHashMap.containsKey(hashTagName))
                .map(HashTag::new)
                .toList();
    }

    private Map<String, HashTag> convertHashTagMap(List<HashTag> existHashTags) {
        return existHashTags.stream()
                .collect(Collectors.toMap(
                        HashTag::getHashTagName,
                        Function.identity()
                ));
    }

    private void saveNonExistHashTagsAndSetHashTagIds(List<HashTag> nonExistHashTags) {
        if (!nonExistHashTags.isEmpty()) {
            hashTagMapper.saveAll(nonExistHashTags);
        }
    }

    private List<Long> unionHashTagIds(List<HashTag> existingHashTags, List<HashTag> nonExistHashTags) {
        List<Long> hashTagIds = new ArrayList<>();

        hashTagIds.addAll(extractHashTagIds(existingHashTags));
        hashTagIds.addAll(extractHashTagIds(nonExistHashTags));

        return hashTagIds;
    }

    private List<Long> extractHashTagIds(List<HashTag> existingHashMap) {
        return existingHashMap
                .stream()
                .map(HashTag::getHashTagId)
                .toList();
    }
}
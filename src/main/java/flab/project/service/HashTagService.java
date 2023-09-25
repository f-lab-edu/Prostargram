package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.mapper.HashTagMapper;

import java.util.*;
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

    public Set<Long> findHashTagIdsByHashTagNames(Set<String> hashTagNames) {
        Set<HashTag> existingHashTags = hashTagMapper.getHashTagsByHashtagNames(hashTagNames);

        Set<HashTag> nonExistHashTags = findNonExistingHashTags(hashTagNames, existingHashTags);

        saveNonExistHashTagsAndSetHashTagIds(nonExistHashTags);

        return unionHashTagIds(existingHashTags, nonExistHashTags);
    }

    private Set<HashTag> findNonExistingHashTags(Set<String> hashTagNames, Set<HashTag> existingHashTags) {
        Map<String, HashTag> existingHashMap = convertHashTagMap(existingHashTags);

        return hashTagNames.stream()
                .filter((hashTagName) -> !existingHashMap.containsKey(hashTagName))
                .map(HashTag::new)
                .collect(Collectors.toSet());
    }

    private Map<String, HashTag> convertHashTagMap(Set<HashTag> existHashTags) {
        return existHashTags.stream()
                .collect(Collectors.toMap(
                        HashTag::getHashTagName,
                        Function.identity()
                ));
    }

    private void saveNonExistHashTagsAndSetHashTagIds(Set<HashTag> nonExistHashTags) {
        if (!nonExistHashTags.isEmpty()) {
            hashTagMapper.saveAll(nonExistHashTags);
        }
    }

    private Set<Long> unionHashTagIds(Set<HashTag> existingHashTags, Set<HashTag> nonExistHashTags) {
        Set<Long> hashTagIds = new HashSet<>();

        hashTagIds.addAll(extractHashTagIds(existingHashTags));
        hashTagIds.addAll(extractHashTagIds(nonExistHashTags));

        return hashTagIds;
    }

    private Set<Long> extractHashTagIds(Set<HashTag> existingHashMap) {
        return existingHashMap
                .stream()
                .map(HashTag::getHashTagId)
                .collect(Collectors.toSet());
    }
}
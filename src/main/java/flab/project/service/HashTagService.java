package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.mapper.HashTagMapper;

import java.util.*;
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
        // TODO hashTagNames 없으면 어애 되는지 보기.
        // TODO nonExistHashTags 없으면 어애 되는지 보기
        Set<HashTag> existingHashTags = hashTagMapper.getHashTagsByHashtagNames(hashTagNames);

        Set<HashTag> nonExistHashTags = findNonExistingHashTags(hashTagNames, existingHashTags);

        saveNonExistHashTagsAndSetHashTagIds(nonExistHashTags);

        return unionHashTagIds(existingHashTags, nonExistHashTags);
    }

    private Set<HashTag> findNonExistingHashTags(Set<String> hashTagNames, Set<HashTag> existingHashTags) {
        Set<String> existingHashTagNames = extractHashTagNames(existingHashTags);

        return hashTagNames.stream()
                .filter(hashtagName -> !existingHashTagNames.contains(hashtagName))
                .map(HashTag::new)
                .collect(Collectors.toSet());
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

    private static Set<String> extractHashTagNames(Set<HashTag> existingHashTags) {
        return existingHashTags.stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toSet());
    }

    private Set<Long> extractHashTagIds(Set<HashTag> existingHashMap) {
        return existingHashMap.stream()
                .map(HashTag::getHashTagId)
                .collect(Collectors.toSet());
    }
}
package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.mapper.HashTagMapper;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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

    // todo 이거 테스트 코드 어떻게 짜야하지?
    public Set<Long> findHashTagIdsByHashTagNames(Set<String> hashTagNames) {
        if (ObjectUtils.isEmpty(hashTagNames)) {
            //todo to 멘토님, PostHashTagService.saveAll()에서는 hashTagNames이 없으면 메서드를 종료 시켰는데 여기서는 반환타입이 Set이라서
            //todo null을 반환하는데 null을 반환하는건 위험한거 같은데 어떻게 느끼시나요?
            return null;
        }

        Set<HashTag> existingHashTags = hashTagMapper.getHashTagsByHashtagNames(hashTagNames);

        Set<HashTag> nonExistingHashTags = findNonExistingHashTags(hashTagNames, existingHashTags);

        saveNonExistingHashTags(nonExistingHashTags);

        return unionHashTagIds(existingHashTags, nonExistingHashTags);
    }

    private Set<HashTag> findNonExistingHashTags(Set<String> hashTagNames, Set<HashTag> existingHashTags) {
        Set<String> existingHashTagNames = extractHashTagNames(existingHashTags);

        return hashTagNames.stream()
                .filter(hashtagName -> !existingHashTagNames.contains(hashtagName))
                .map(HashTag::new)
                .collect(Collectors.toSet());
    }

    private void saveNonExistingHashTags(Set<HashTag> nonExistingHashTags) {
        if (ObjectUtils.isNotEmpty(nonExistingHashTags)) {
            hashTagMapper.saveAll(nonExistingHashTags);
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
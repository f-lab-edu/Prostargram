package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.mapper.HashTagMapper;
import flab.project.mapper.PostHashTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostHashTagService {

    private final HashTagMapper hashTagMapper;
    private final PostHashTagMapper postHashTagMapper;

    public void saveAll(long postId, List<String> hashTagNames) {
        List<HashTag> existingHashTags = hashTagMapper.getHashTagsByHashtagNames(hashTagNames);

        Map<String, HashTag> existingHashMap = convertHashTagMap(existingHashTags);


        List<HashTag> nonExistHashTags = findNonExistingHashTags(hashTagNames, existingHashMap);

        saveNonExistHashTagsAndSetHashTagIds(nonExistHashTags);

        List<Long> hashTagIds = unionHashTagIds(existingHashTags, nonExistHashTags);

        int NumberOfAffectedRow = postHashTagMapper.saveAll(postId, hashTagIds);

        if (NumberOfAffectedRow != hashTagIds.size()) {
            throw new RuntimeException();
        }
    }

    private List<Long> unionHashTagIds(List<HashTag> existingHashTags, List<HashTag> nonExistHashTags) {
        List<Long> hashTagIds = new ArrayList<>();

        hashTagIds.addAll(extractHashTagIds(existingHashTags));
        hashTagIds.addAll(extractHashTagIds(nonExistHashTags));

        return hashTagIds;
    }

    private void saveNonExistHashTagsAndSetHashTagIds(List<HashTag> nonExistHashTags) {
        if(!nonExistHashTags.isEmpty()){
            hashTagMapper.saveAll(nonExistHashTags);
        }
    }

    private static List<HashTag> findNonExistingHashTags(List<String> hashTagNames, Map<String, HashTag> existingHashMap) {
        return hashTagNames.stream()
                .filter((hashTagName) -> !existingHashMap.containsKey(hashTagName))
                .map(HashTag::new)
                .collect(Collectors.toList());
    }

    private List<Long> extractHashTagIds(List<HashTag> existingHashMap) {
        return existingHashMap
                .stream()
                .map(HashTag::getHashTagId)
                .toList();
    }

    private Map<String, HashTag> convertHashTagMap(List<HashTag> existHashTags) {
        return existHashTags.stream()
                .collect(Collectors.toMap(
                        HashTag::getHashTagName,
                        Function.identity()
                ));
    }
}
package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.mapper.HashTagMapper;
import flab.project.mapper.PostHashTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostHashTagService {

    private final HashTagMapper hashTagMapper;
    private final PostHashTagMapper postHashTagMapper;

    public void saveAll(List<String> hashTagNames) {
        List<HashTag> existHashTags = hashTagMapper.getHashTagsByHashtagNames(hashTagNames);

        Map<String, Long> existHashTagMap = existHashTags.stream()
                .collect(Collectors.toMap(
                        HashTag::getHashTagName,
                        HashTag::getHashTagId
                ));


        List<Long> hashTagIds = existHashTagMap.values().stream().toList();

        List<String> notExistHashTagNames = hashTagNames.stream()
                .filter((hashTagName) -> !existHashTagMap.containsKey(hashTagName))
                .toList();

        if(!notExistHashTagNames.isEmpty()){
            List<Long> addedHashTagIds = hashTagMapper.saveAll(notExistHashTagNames);
            hashTagIds.addAll(addedHashTagIds);
        }
//
//        int NumberOfAffectedRow = postHashTagMapper.saveAll(hashTagIds);
//
//        if (NumberOfAffectedRow != 1) {
//            throw new RuntimeException();
//        }
    }
}

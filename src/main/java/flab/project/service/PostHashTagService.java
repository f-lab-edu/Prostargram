package flab.project.service;

import flab.project.mapper.HashTagMapper;
import flab.project.mapper.PostHashTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostHashTagService {

    private final HashTagMapper hashTagMapper;
    private final PostHashTagMapper postHashTagMapper;

    public void saveAll(List<String> hashTagNames) {
        HashMap<String, Long> existHashTagMap = hashTagMapper.getHashtagIdsByHashtagNames(hashTagNames);
        List<Long> hashTagIds = existHashTagMap.values().stream().toList();


        List<String> notExistHashTagNames = hashTagNames.stream()
                .filter((hashTagName) -> !existHashTagMap.containsKey(hashTagName))
                .collect(Collectors.toList());

        if(!notExistHashTagNames.isEmpty()){
            List<Long> addedHashTagIds = hashTagMapper.saveAll(notExistHashTagNames);
            hashTagIds.addAll(addedHashTagIds);
        }

        int NumberOfAffectedRow = postHashTagMapper.saveAll(hashTagIds);

        if (NumberOfAffectedRow != 1) {
            throw new RuntimeException();
        }
    }
}

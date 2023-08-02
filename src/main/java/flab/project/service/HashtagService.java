package flab.project.service;

import flab.project.data.InterestsDelta;
import flab.project.mapper.HashtagMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagMapper hashtagMapper;

    public void insertNonExistHashtag(InterestsDelta interestsDelta) {
        if (interestsDelta == null) {
            return;
        }

        List<String> toAddInterests = interestsDelta.getToAddInterests();
        List<String> nonExistInterests = extractNonExistHahtagFromToAddInterests(toAddInterests);

        hashtagMapper.insertAll(nonExistInterests);
    }

    private List<String> extractNonExistHahtagFromToAddInterests(List<String> toAddInterests) {
        List<String> existedHashtag = hashtagMapper.retrieveHashtagsIn(toAddInterests);

        return toAddInterests.stream()
            .filter(interest -> !existedHashtag.contains(interest))
            .toList();
    }
}

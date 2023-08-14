package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.mapper.HashtagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HashtagService {
    private final HashtagMapper hashtagMapper;

    public Long getHashtagIdByHashtagName(String interestName) {
        return hashtagMapper.getHashtagIdByHashtagName(interestName);
    }

    public long addHashtag(String interestName) {
        HashTag hashTag = new HashTag(interestName);
        hashtagMapper.save(hashTag);

        return hashTag.getHashTagId();
    }
}

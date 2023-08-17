package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.facade.InterestFacade;
import flab.project.mapper.HashtagMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class HashtagServiceTest {
    @InjectMocks
    private HashtagService hashtagService;
    @Mock
    private HashtagMapper hashtagMapper;

    @DisplayName("해시태그 이름을 통해 해시태그 아이디를 가져올 수 있다.")
    @Test
    void getHashtagIdByHashtagName() {
        String interestName = "testInterestName";
        hashtagService.getHashtagIdByHashtagName(interestName);

        then(hashtagMapper).should().getHashtagIdByHashtagName(interestName);
    }

    @DisplayName("해시태그를 추가할 수 있다.")
    @Test
    void addHashtag() {
        HashTag hashTag = new HashTag("testInterestName");

        hashtagService.addHashtag(hashTag);

        then(hashtagMapper).should().save(hashTag);
    }
}
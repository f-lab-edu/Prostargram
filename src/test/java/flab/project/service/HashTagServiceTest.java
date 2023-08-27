package flab.project.service;

import flab.project.data.dto.model.HashTag;
import flab.project.mapper.HashTagMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class HashTagServiceTest {

    @InjectMocks
    private HashTagService hashtagService;
    @Mock
    private HashTagMapper hashtagMapper;

    @DisplayName("해시태그 이름을 통해 해시태그 아이디를 가져올 수 있다.")
    @Test
    void getHashtagIdByHashtagName() {
        // given
        String interestName = "testInterestName";

        // when
        hashtagService.getHashTagIdByHashtagName(interestName);

        // then
        then(hashtagMapper).should().getHashtagIdByHashtagName(interestName);
    }

    @DisplayName("해시태그를 추가할 수 있다.")
    @Test
    void addHashtag() {
        // given
        HashTag hashTag = new HashTag("testInterestName");

        // when
        hashtagService.addHashTag(hashTag);

        // then
        then(hashtagMapper).should().save(hashTag);
    }
}
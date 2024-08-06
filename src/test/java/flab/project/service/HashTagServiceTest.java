package flab.project.service;

import flab.project.domain.post.model.HashTag;
import flab.project.domain.post.service.HashTagService;
import flab.project.domain.post.mapper.HashTagMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class HashTagServiceTest {

    @InjectMocks
    private HashTagService hashTagService;
    @Mock
    private HashTagMapper hashTagMapper;

    @DisplayName("해시태그 이름을 통해 해시태그 아이디를 가져올 수 있다.")
    @Test
    void getHashtagIdByHashtagName() {
        // given
        String interestName = "testInterestName";

        // when
        hashTagService.getHashTagIdByHashtagName(interestName);

        // then
        then(hashTagMapper).should().getHashtagIdByHashtagName(interestName);
    }

    @DisplayName("해시태그를 추가할 수 있다.")
    @Test
    void addHashtag() {
        // given
        HashTag hashTag = new HashTag("testInterestName");

        // when
        hashTagService.addHashTag(hashTag);

        // then
        then(hashTagMapper).should().save(hashTag);
    }

    @DisplayName("해시태그 이름들로 해시태그 id들을 가져올 수 있다.")
    @Test
    void findHashTagIdsByHashTagNames() {
        // given
        Set<String> hashTagNames = Set.of("#test1", "#test2");
        Set<HashTag> retrievedHashTags = Set.of(
                HashTag.builder()
                        .hashTagId(1L)
                        .hashTagName("#test1")
                        .build()
        );

        given(hashTagMapper.getHashTagsByHashtagNames(hashTagNames)).willReturn(retrievedHashTags);

        // when
        hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        // then
        then(hashTagMapper).should().saveAll(ArgumentMatchers.<Set<HashTag>>any());
    }

    @DisplayName("해시태그 이름들로 해시태그 id들을 가져올 때, nonExistingHashTags가 없으면 hashTagMapper의 saveAll()메서드가 호출 되지 않는다.")
    @Test
    void findHashTagIdsByHashTagNames_emptyNonExistHashTags() {
        // given
        Set<String> hashTagNames = Set.of("#test1", "#test2");
        Set<HashTag> retrievedHashTags = Set.of(
                HashTag.builder()
                        .hashTagId(1L)
                        .hashTagName("#test1")
                        .build(),
                HashTag.builder()
                        .hashTagId(2L)
                        .hashTagName("#test2")
                        .build()
        );

        given(hashTagMapper.getHashTagsByHashtagNames(hashTagNames)).willReturn(retrievedHashTags);

        // when
        hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        // then
        then(hashTagMapper).shouldHaveNoMoreInteractions();
    }

    @DisplayName("해시태그 이름들로 해시태그 id들을 가져올 때, hashTagNames가 null이면 empty set이 반환된다.")
    @Test
    void findHashTagIdsByHashTagNames_nullHashTagNames() {
        // given
        Set<String> hashTagNames = null;

        // when
        Set<Long> hashTagIds = hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        // then
        assertThat(hashTagIds).isEmpty();
    }

    @DisplayName("해시태그 이름들로 해시태그 id들을 가져올 때, hashTagNames가 empty set이면 empty set이 반환된다.")
    @Test
    void findHashTagIdsByHashTagNames_emptyHashTagNames() {
        // given
        Set<String> hashTagNames = new HashSet<>();

        // when
        Set<Long> hashTagIds = hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        // then
        assertThat(hashTagIds).isEmpty();
    }

    @DisplayName("해시태그 이름들로 해시태그 id들을 가져올 때, 매개변수로 넘어간 hashTagNames와 existingHashTags를 비교하여 nonExistingHashTags를 찾아낼 수 있다.")
    @Test
    void findNonExistingHashTags() {
        // given
        Set<String> hashTagNames = Set.of("#test1", "#test2");
        Set<HashTag> retrievedHashTags = Set.of(
                HashTag.builder()
                        .hashTagId(1L)
                        .hashTagName("#test1")
                        .build()
        );

        given(hashTagMapper.getHashTagsByHashtagNames(hashTagNames)).willReturn(retrievedHashTags);
        Class<Set<HashTag>> setClass =
                (Class<Set<HashTag>>)(Class)Set.class;
        ArgumentCaptor<Set<HashTag>> captor = ArgumentCaptor.forClass(setClass);

        // when
        hashTagService.findHashTagIdsByHashTagNames(hashTagNames);

        // then
        then(hashTagMapper).should().saveAll(captor.capture());
        HashTag nonExistingHashTag = captor.getValue().stream().toList().get(0);
        assertThat(nonExistingHashTag.getHashTagName()).isEqualTo("#test2");
    }
}
package flab.project.service;

import flab.project.config.exception.ExceedMaxSizeOfPostHashTagsException;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostHashTagMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static flab.project.common.Constraints.MAX_LENGTH_OF_HASHTAGS;
import static flab.project.common.Constraints.MAX_SIZE_OF_POST_HASHTAGS;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostHashTagServiceTest {

    @InjectMocks
    PostHashTagService postHashTagService;
    @Mock
    HashTagService hashTagService;
    @Mock
    PostHashTagMapper postHashTagMapper;

    @DisplayName("게시물에 해시태그를 추가할 수 있다.")
    @Test
    void saveAll() {
        // given
        long postId = 1L;
        Set<String> hashTagNames = Set.of("#test1", "#test2");
        Set<Long> retrievedHashTagIds = Set.of(1L, 2L);

        // when
        given(hashTagService.findHashTagIdsByHashTagNames(hashTagNames))
                .willReturn(retrievedHashTagIds);
        given(postHashTagMapper.saveAll(postId, retrievedHashTagIds))
                .willReturn(retrievedHashTagIds.size());

        // when & then
        assertThatCode(() -> postHashTagService.saveAll(postId, hashTagNames))
                .doesNotThrowAnyException();
    }

    @DisplayName("게시물에 해시태그를 추가할 때, DB반영에 실패했으면 RuntimeException을 던진다.")
    @Test
    void saveAll_failAffectToDB() {
        // given
        long postId = 1L;
        Set<String> hashTagNames = Set.of("#test1", "#test2");
        Set<Long> retrievedHashTagIds = Set.of(1L, 2L);

        // when
        given(hashTagService.findHashTagIdsByHashTagNames(hashTagNames))
                .willReturn(retrievedHashTagIds);
        given(postHashTagMapper.saveAll(postId, retrievedHashTagIds))
                .willReturn(retrievedHashTagIds.size() - 1);

        // when & then
        assertThatCode(() -> postHashTagService.saveAll(postId, hashTagNames))
                .isExactlyInstanceOf(RuntimeException.class);
    }

    @DisplayName("게시물에 해시태그를 추가할 때, postId가 음수면 InvalidUserInputException을 던진다.")
    @Test
    void saveAll_negativePostId() {
        // given
        long negativePostId = -1L;
        Set<String> hashTagNames = Set.of("#test1", "#test2");

        // when & then
        assertThatCode(() -> postHashTagService.saveAll(negativePostId, hashTagNames))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물에 해시태그를 추가할 때, hashTagNames가 최대 개수를 초과하면 ExceedMaxSizeOfPostHashTagsException을 던진다.")
    @Test
    void saveAll_exceedMaxSizeOfPostHashTags() {
        // given
        long postId = 1L;
        Set<String> hashTagNamesExceededMaxSize = new HashSet<>();

        for (int i = 0; i < MAX_SIZE_OF_POST_HASHTAGS + 1; i++) {
            hashTagNamesExceededMaxSize.add("#test" + i);
        }

        // when & then
        assertThatCode(() -> postHashTagService.saveAll(postId, hashTagNamesExceededMaxSize))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물에 해시태그를 추가할 때, hashTagNames중 최대 길이를 초과한 hashTagName이 있으면 ExceedMaxSizeOfPostHashTagsException을 던진다.")
    @Test
    void saveAll_exceedLengthOfPostHashTagName() {
        // given
        long postId = 1L;
        String hashTagNameExceededMaxLength = RandomStringUtils.randomAlphanumeric(MAX_LENGTH_OF_HASHTAGS + 1);
        Set<String> invalidHashTagNames = Set.of("#test1", hashTagNameExceededMaxLength);

        // when & then
        assertThatCode(() -> postHashTagService.saveAll(postId, invalidHashTagNames))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }
}
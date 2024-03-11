package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostHashTagMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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

        // when & then
        postHashTagService.saveAll(postId, hashTagNames);
        then(postHashTagMapper).should().saveAll(postId, retrievedHashTagIds);
    }

    @DisplayName("게시물에 해시태그를 추가할 때, postId가 음수면 InvalidUserInput 예외를 던진다.")
    @Test
    void saveAll_negativePostId() {
        // given
        long negativePostId = -1L;
        Set<String> hashTagNames = Set.of("#test1", "#test2");

        // when
        assertThatCode(() -> postHashTagService.saveAll(negativePostId, hashTagNames))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물에 해시태그를 추가할 때, postId가 0이면 InvalidUserInput 예외를 던진다.")
    @Test
    void saveAll_zeroPostId() {
        // given
        long zeroPostId = 0L;
        Set<String> hashTagNames = Set.of("#test1", "#test2");

        // when
        assertThatCode(() -> postHashTagService.saveAll(zeroPostId, hashTagNames))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("게시물에 해시태그를 추가할 때, hashTagNames가 null이면 함수가 종료된다.")
    @Test
    void saveAll_nullHashTagNames() {
        // given
        long postId = 1L;
        Set<String> hashTagNames = null;

        // when
        postHashTagService.saveAll(postId, hashTagNames);

        then(hashTagService).shouldHaveNoInteractions();
        then(postHashTagMapper).shouldHaveNoInteractions();
    }

    @DisplayName("게시물에 해시태그를 추가할 때, hashTagNames가 empty Set이면 함수가 종료된다.")
    @Test
    void saveAll_emptyHashTagNames() {
        // given
        long postId = 1L;
        Set<String> hashTagNames = new HashSet<>();

        // when
        postHashTagService.saveAll(postId, hashTagNames);

        then(hashTagService).shouldHaveNoInteractions();
        then(postHashTagMapper).shouldHaveNoInteractions();
    }
}
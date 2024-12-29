package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.post.service.PostImageService;
import flab.project.domain.post.mapper.PostImageMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostImageServiceTest {

    @InjectMocks
    PostImageService postImageService;
    @Mock
    PostImageMapper postImageMapper;

    @DisplayName("업로드된 게시물의 주소를 DB에 저장한다.")
    @Test
    void saveAll() {
        // given
        long postId = 1L;
        Set<String> uploadedFileUrls = Set.of("https://test1.com", "https://test2.com");

        // when
        postImageService.saveAll(postId, uploadedFileUrls);
        then(postImageMapper).should().saveAll(postId, uploadedFileUrls);
    }

    @DisplayName("postId가 양수가 아니면 예외를 던진다.")
    @Test
    void saveAll_negativePostId() {
        // given
        long negativePostId = -1L;
        Set<String> uploadedFileUrls = Set.of("https://test1.com", "https://test2.com");

        // when
        assertThatCode(() -> postImageService.saveAll(negativePostId, uploadedFileUrls))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("uploadedFileUrls가 Empty Set이면 InvalidUserInput을 던진다.")
    @Test
    void saveAll_EmptyFileUrls() {
        // given
        long postId = 1L;
        Set<String> uploadedFileUrls = new HashSet<>();

        // when
        assertThatCode(() -> postImageService.saveAll(postId, uploadedFileUrls))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("uploadedFileUrls가 null이면 InvalidUserInput을 던진다.")
    @Test
    void saveAll_NullFileUrls() {
        // given
        long postId = 1L;
        Set<String> uploadedFileUrls = null;

        // when
        assertThatCode(() -> postImageService.saveAll(postId, uploadedFileUrls))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("공백 으로만 이루 어진 uploadedFileUrl가 있으면 InvalidUserInput을 던진다.")
    @Test
    void saveAll_OnlyBlankUploadedFileUrl() {
        // given
        long postId = 1L;
        Set<String> uploadedFileUrlsWithOnlyBlank= Set.of("   ", "#test1");

        // when
        assertThatCode(() -> postImageService.saveAll(postId, uploadedFileUrlsWithOnlyBlank))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("비어 있는 문자열의 uploadedFileUrl가 있으면 InvalidUserInput을 던진다.")
    @Test
    void saveAll_EmptyFileUrl() {
        // given
        long postId = 1L;
        Set<String> uploadedFileUrlsWithOnlyBlank= Set.of("", "#test1");

        // when
        assertThatCode(() -> postImageService.saveAll(postId, uploadedFileUrlsWithOnlyBlank))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("이미지 주소를 수정할 수 있다.")
    @Test
    void updatePostImageUrls() {
        // given
        long postId = 1L;
        Set<String> currentPostImageUrls = Set.of("https://test1.com","https://test2.com");
        Set<String> newPostImageUrls = Set.of("https://test2.com","https://test3.com");
        Set<String> shouldRemoveImageUrls = Set.of("https://test1.com");
        Set<String> shouldSaveImageUrls = Set.of("https://test3.com");

        given(postImageMapper.findAllByPostId(postId))
                .willReturn(currentPostImageUrls);

        ArgumentCaptor<Long> postIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Set<String>> imageUrlsCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        postImageService.update(postId, newPostImageUrls);

        // then
        verify(postImageMapper).removeAll(postIdCaptor.capture(), imageUrlsCaptor.capture());
        assertThat(imageUrlsCaptor.getValue()).isEqualTo(shouldRemoveImageUrls);

        verify(postImageMapper).saveAll(postIdCaptor.capture(), imageUrlsCaptor.capture());
        assertThat(imageUrlsCaptor.getValue()).isEqualTo(shouldSaveImageUrls);
    }
}
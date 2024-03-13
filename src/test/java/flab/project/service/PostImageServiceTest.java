package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.PostImageMapper;
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
}
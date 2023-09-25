package flab.project.service;

import flab.project.mapper.PostImageMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostImageServiceTest {

    @InjectMocks
    PostImageService postImageService;
    @Mock
    PostImageMapper postImageMapper;

    @DisplayName("업로드된 게시물의 주소를 DB에 저장한다.")
    @Test
    void saveAll(){
        // given
        long postId = 1L;
        Set<String> uploadedFileUrls = Set.of("https://test1.com", "https://test2.com");

        given(postImageMapper.saveAll(postId, uploadedFileUrls))
                .willReturn(uploadedFileUrls.size());

        // when
        assertThatCode(() -> postImageService.saveAll(postId,uploadedFileUrls))
                .doesNotThrowAnyException();
    }

    @DisplayName("업로드된 게시물의 주소를 DB에 저장한다.")
    @Test
    void saveAll_failReflectToDB(){
        // given
        long postId = 1L;
        Set<String> uploadedFileUrls = Set.of("https://test1.com", "https://test2.com");

        given(postImageMapper.saveAll(postId, uploadedFileUrls))
                .willReturn(uploadedFileUrls.size()-1);

        // when
        assertThatCode(() -> postImageService.saveAll(postId,uploadedFileUrls))
                .isExactlyInstanceOf(RuntimeException.class);
    }
}
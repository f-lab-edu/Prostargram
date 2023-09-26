package flab.project.service;

import flab.project.mapper.PostOptionMapper;
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
class PostOptionServiceTest {

    private static final long validPostId = 1L;
    private static final Set<String> validOptionContents = Set.of("test1", "test2");

    @InjectMocks
    PostOptionService postOptionService;
    @Mock
    PostOptionMapper postOptionMapper;

    @DisplayName("게시물의 선택지를 저장할 수 있다. postOptionMapper.saveAll()메서드의 반환값이 postOptionContents와 같으면 예외가 발생하지 않는다.")
    @Test
    void savePostOptions() {
        // given
        given(postOptionMapper.saveAll(validPostId, validOptionContents)).willReturn(validOptionContents.size());

        // when & then
        assertThatCode(() -> postOptionService.savePostOptions(validPostId, validOptionContents))
                .doesNotThrowAnyException();
    }

    @DisplayName("게시물의 선택지를 저장할 때, postOptionMapper.saveAll()메서드의 반환값이 postOptionContents와 다르면 RuntimeException을 던진다.")
    @Test
    void savePostOptions_failToAffectDB() {
        // given
        given(postOptionMapper.saveAll(validPostId, validOptionContents)).willReturn(validOptionContents.size()-1);

        // when & then
        assertThatCode(() -> postOptionService.savePostOptions(validPostId, validOptionContents))
                .isExactlyInstanceOf(RuntimeException.class);
    }
}
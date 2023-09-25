package flab.project.service;

import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.mapper.PostMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    PostService postService;
    @Mock
    PostMapper postMapper;

    @DisplayName("게시물을 추가할 수 있다.")
    @Test
    void addPost() {
        // given
        long userId = 1;
        int numberOfAffectedRow = 1;

        AddDebatePostRequest addPostRequest = AddDebatePostRequest.builder()
                .postId(1L)
                .content("게시물 내용")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("내용물1", "내용물2"))
                .build();

        given(postMapper.save(userId, addPostRequest))
                .willReturn(numberOfAffectedRow);

        // when & then
        assertThatCode(() -> postService.addPost(userId, addPostRequest))
                .doesNotThrowAnyException();

        then(postMapper).should().save(userId, addPostRequest);
    }

    @DisplayName("게시물을 추가할 때, DB에 반영이 실패 했으면 RuntimeException을 던진다.")
    @Test
    void addPost_failAffectToDB() {
        // given
        long userId = 1;
        int numberOfAffectedRow = 2;

        AddDebatePostRequest addPostRequest = AddDebatePostRequest.builder()
                .postId(1L)
                .content("게시물 내용")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("내용물1", "내용물2"))
                .build();

        given(postMapper.save(userId, addPostRequest))
                .willReturn(numberOfAffectedRow);

        // when & then
        assertThatThrownBy(() -> postService.addPost(userId, addPostRequest))
                .isExactlyInstanceOf(RuntimeException.class);
    }
}
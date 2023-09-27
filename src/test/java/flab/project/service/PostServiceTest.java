package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
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

        AddDebatePostRequest addPostRequest = AddDebatePostRequest.builder()
                .postId(1L)
                .content("게시물 내용")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("내용물1", "내용물2"))
                .build();

        // when & then
        postService.addPost(userId, addPostRequest);
        then(postMapper).should().save(userId, addPostRequest);
    }

    @DisplayName("게시물을 추가할 때, userId가 양수가 아니면 InvalidUserInput 예외를 던진다.")
    @Test
    void addPost_negativeUserId() {
        // given
        long userId = -1;

        AddDebatePostRequest addPostRequest = AddDebatePostRequest.builder()
                .postId(1L)
                .content("게시물 내용")
                .hashTagNames(Set.of("#test1", "#test2"))
                .optionContents(Set.of("내용물1", "내용물2"))
                .build();

        // when & then
        assertThatCode(() -> postService.addPost(userId, addPostRequest))
                .isExactlyInstanceOf(InvalidUserInputException.class);
    }
}
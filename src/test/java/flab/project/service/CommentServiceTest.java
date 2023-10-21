package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Comment;
import flab.project.mapper.CommentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @Mock
    CommentMapper commentMapper;

    @DisplayName("댓글을 작성할 수 있다.")
    @Test
    void addComment_isComment() {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = null;
        String content = "안녕하세요.";

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        // when
        commentService.addComment(postId, userId, parentId, content);

        // then
        then(commentMapper).should().addComment(comment);
    }

    @DisplayName("대댓글을 작성할 수 있다.")
    @Test
    void addComment_isReply() {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        // when
        commentService.addComment(postId, userId, parentId, content);

        // then
        then(commentMapper).should().addComment(comment);
    }

    @DisplayName("댓글을 작성할 때, postId가 양수가 아니라면 InvalidUserInputException을 반환한다.")
    @Test
    void addComment_invalidPostId() {
        // given
        long negativePostId = -1L;
        long userId = 1L;
        Long parentId = null;
        String content = "안녕하세요.";

        // when & then
        assertThatThrownBy(() -> commentService.addComment(negativePostId, userId, parentId, content))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("대댓글을 작성할 때, postId가 양수가 아니라면 InvalidUserInputException을 반환한다.")
    @Test
    void addReply_invalidPostId() {
        // given
        long negativePostId = -1L;
        long userId = 1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        // when & then
        assertThatThrownBy(() -> commentService.addComment(negativePostId, userId, parentId, content))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("댓글을 작성할 때, userId가 양수가 아니라면 InvalidUserInputException을 반환한다.")
    @Test
    void addComment_invalidUserId() {
        // given
        long postId = 1L;
        long negativeUserId = -1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        // when & then
        assertThatThrownBy(() -> commentService.addComment(postId, negativeUserId, parentId, content))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("대댓글을 작성할 때, userId가 양수가 아니라면 InvalidUserInputException을 반환한다.")
    @Test
    void addReply_invalidUserId() {
        // given
        long postId = 1L;
        long negativeUserId = -1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        // when & then
        assertThatThrownBy(() -> commentService.addComment(postId, negativeUserId, parentId, content))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("대댓글을 작성할 때, parentId가 양수가 아니라면 InvalidUserInputException을 반환한다.")
    @Test
    void addReply_invalidParentId() {
        // given
        long postId = 1L;
        long userId = 1L;
        Long negativeParentId = -1L;
        String content = "안녕하세요.";

        // when & then
        assertThatThrownBy(() -> commentService.addComment(postId, userId, negativeParentId,content))
                .isInstanceOf(InvalidUserInputException.class);
    }
}

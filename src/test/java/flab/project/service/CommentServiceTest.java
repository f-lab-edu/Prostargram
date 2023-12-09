package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Comment;
import flab.project.mapper.CommentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentMapper commentMapper;

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

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

        // when
        commentService.addComment(postId, userId, parentId, content);

        // then
        then(commentMapper).should().addComment(commentCaptor.capture());

        Comment captorValue = commentCaptor.getValue();
        assertThat(comment)
                .extracting(Comment::getPostId, Comment::getUserId,
                        Comment::getParentId, Comment::getContent)
                .containsExactly(captorValue.getPostId(), captorValue.getUserId(),
                        captorValue.getParentId(), captorValue.getContent());
    }

    @DisplayName("대댓글을 작성할 수 있다.")
    @Test
    void addComment_isReply() {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = 1L;
        String content = "안녕하세요.";

        Comment comment = Comment.builder().postId(postId).userId(userId).parentId(parentId).content(content).build();

        // when
        commentService.addComment(postId, userId, parentId, content);

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

        // then
        then(commentMapper).should().addComment(commentCaptor.capture());

        Comment captorValue = commentCaptor.getValue();
        assertThat(comment)
                .extracting(Comment::getPostId, Comment::getUserId,
                        Comment::getParentId, Comment::getContent)
                .containsExactly(captorValue.getPostId(), captorValue.getUserId(),
                        captorValue.getParentId(), captorValue.getContent());
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

    @DisplayName("대댓글을 작성할 때, parentId가 양수가 아니라면 InvalidUserInputException을 반환한다.")
    @Test
    void addReply_invalidParentId() {
        // given
        long postId = 1L;
        long userId = 1L;
        Long negativeParentId = -1L;
        String content = "안녕하세요.";

        // when & then
        assertThatThrownBy(() -> commentService.addComment(postId, userId, negativeParentId, content))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("lastCommentId가 없을 경우, 즉 처음에 댓글을 가져올 수 있다.")
    @Test
    void getComments_lastCommentIdIsNull() {
        // given
        long postId = 1L;
        Long lastCommentId = null;
        long limit = 1L;

        // when
        commentService.getComments(postId, lastCommentId, limit);

        // then
        then(commentMapper).should().getComments(postId, lastCommentId, limit);
    }

    @DisplayName("댓글을 가져올 수 있다.")
    @Test
    void getComments() {
        // given
        long postId = 1L;
        Long lastCommentId = 11L;
        long limit = 1L;

        // when
        commentService.getComments(postId, lastCommentId, limit);

        // then
        then(commentMapper).should().getComments(postId, lastCommentId, limit);
    }

    @DisplayName("댓글을 가져올 때, postId가 양수가 아니라면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getComments_invalidPostId() {
        // given
        long invalidPostId = -1L;
        Long lastCommentId = null;
        long limit = 1L;

        // when & then
        assertThatThrownBy(() -> commentService.getComments(invalidPostId, lastCommentId, limit))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("댓글을 가져올 때, lastCommentId가 양수가 아니라면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getComments_invalidLastCommentId() {
        // given
        long postId = 1L;
        Long invalidLastCommentId = -1L;
        long limit = 1L;

        // when & then
        assertThatThrownBy(() -> commentService.getComments(postId, invalidLastCommentId, limit))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("댓글을 가져올 때, limit의 값이 양수가 아니라면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getComments_negativeLimit() {
        // given
        long postId = 1L;
        Long lastCommentId = null;
        long negativeLimit = -1L;

        // when & then
        assertThatThrownBy(() -> commentService.getComments(postId, lastCommentId, negativeLimit))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("댓글을 가져올 때, limit의 값이 10을 초과한다면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getComments_excessLimit() {
        // given
        long postId = 1L;
        Long lastCommentId = null;
        Long invalidLimit = 11L;

        // when & then
        assertThatThrownBy(() -> commentService.getComments(postId, lastCommentId, invalidLimit))
                .isInstanceOf(InvalidUserInputException.class);
    }
}

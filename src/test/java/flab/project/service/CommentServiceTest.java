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

    @DisplayName("댓글을 작성할 때, content가 공백일 경우 InvalidUserInput 예외를 반환한다.")
    @Test
    void addComment_contentIsZero() {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = null;
        String zeroContent = " ";

        // when & then
        assertThatThrownBy(() -> commentService.addComment(postId, userId, parentId, zeroContent))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("댓글을 작성할 때, content가 공백일 경우 InvalidUserInput 예외를 반환한다.")
    @Test
    void addComment_excessContent() {
        // given
        long postId = 1L;
        long userId = 1L;
        Long parentId = null;
        String excessContent = "댓글 작성하기 API를 작성할 당시가 아닌, 댓글 가져오기 API를 작성하면서 추가된 검증이라 테스트하게 되었습니다. 근데 1000자 채우기는 좀 힘들어서 그냥 아무 내용이나 쓰고 있습니다. 이렇게 해도 되겠죠? 그냥 게임 속 더미 데이터라고 봐주세요. 컨텐트의 길이가 글자의 수랑 대부분 같을테니 지금 글자수 세기를 통해 세고 있는데, 아직도 200자군요.. 평소에 자소서 쓸 땐 쓸 토픽이 잘 생각이 안 나거나 없어서 힘들었는데, 지금은 다른 의미로 그러니까 1000자를 막연하게 채울 생각을 하니 약간 답답하네요. 그래도 깊게 생각하지 않고 그냥 치고 있어서 부담스럽진 않습니다. 그래도 이거 빨리 하고 다른 API 테스트 코드 작성하다가 잘건데, 빨리 1000자가 채워졌으면 하네요. 그래도 아직 500자군요.. 생산적인 이야기를 좀 해봐야겠네요. 이전에 9, 10월은 잘 모르겠는데 구현을 해야했어서 좀 힘들었다면 요즘은 연말이라 붕 뜬 분위기 때문에 집중에 살짝 방해가 되는 것 같아요. 의도치 않은 약속이 많이 잡혔다던가, 벌써 2023년이 다 갔다는 걸 체감해서 그런 것 같기도 하네요. 집중이 안 되어도 저는 그냥 앉아있는 스타일이라 집중하도록 노력해보겠습니다. 그나저나 요즘 생활 패턴이 깨져서 복구하기 위해 수습 중인데, 하루에 1시간씩 잠에 드는 시간을 줄이고 있어요. 이대로면 좋은 결과를 기대할 수도? 하지만 벌써 자부하지는 않을게요. 또 오랜만에 테스트 코드를 짜서 그런지 진짜 어색했네요. 그리고 새삼 오류가 너무 많이 떠서 새삼 놀랐습니다. 얼른 단위 테스트라는 책을 사서 읽고 싶은데 또 일을 늘린 순 없으므로 읽을 날을 기약 중이에요. 이전에 말씀해주신 것처럼 할 일이 많으면 집중력이 분산되어서 각각의 일의 퀄리티가 낮아지는 걸 체감 중이라 적당한 선을 찾아나가고 있네요. 벌써 950자정도 되었으므로 슬슬 끝낼 수 있겠네요. 일단은 위에서 말씀드린 것처럼 의식의 흐름에 따라 사적인 내용이 담긴 글을 작성했는데, 아닌 것 같다 싶으셔서 요청 주시면 내용은 수정하도록 하겠습니다.";

        // when & then
        assertThatThrownBy(() -> commentService.addComment(postId, userId, parentId, excessContent))
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

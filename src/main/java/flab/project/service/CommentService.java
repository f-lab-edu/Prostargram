package flab.project.service;

import flab.project.data.dto.model.Comment;
import flab.project.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public Comment addComment (long postId, long userId, Long rootId, String content) {
        Comment comment = createComment(postId, userId, content);

        commentMapper.addComment(comment);

        setRootId(comment, rootId);

        return comment;
    }

    private Comment createComment(long postId, long userId, String content) {
        LocalDateTime todayTime = LocalDateTime.now();

        return Comment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .createdAt(todayTime)
                .build();
    }

    // Todo 근데 rootId 값이 null일 경우, rootId 값이 commentId 값으로 세팅되는 걸 Swagger UI상에서 확인할 수가 없는데..
    private void setRootId(Comment comment, Long rootId) {
        if (rootId == null) {
            rootId = comment.getCommentId();
        }
        comment.setRootId(rootId);
    }
}
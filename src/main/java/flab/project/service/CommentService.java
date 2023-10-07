package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NotFoundException;
import flab.project.data.dto.model.Comment;
import flab.project.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public Comment addComment(long postId, long userId, Long parentId, String content) {
        validateComment(postId, userId, parentId);

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        if (comment == null) {
            throw new NotFoundException("comment is not found.");
        }

        commentMapper.addComment(comment);

        setRootId(comment, parentId);

        return comment;
    }

    private void validateComment(long postId, long userId, Long parentId) {
        // Todo userId는 추후 삭제 예정이므로 합쳐서 작성
        validatePostIdAndUserId(postId, userId);
        validateParentId(parentId);
    }

    private void validatePostIdAndUserId(long postId, long userId) {
        if (postId <= 0) {
            throw new InvalidUserInputException("Invalid postId.");
        }

        if (userId <= 0) {
            throw new InvalidUserInputException("Invalid userId.");
        }
    }

    private void validateParentId(Long parentId) {
        if (parentId <= 0) {
            throw new InvalidUserInputException("Invalid parentId.");
        }
    }

    // Todo (CommentController 26L) parentId에 commentId를 넣지 않고, null을 그대로 넣는 방식으로 회의 후 수정 예정 / builder에 rootId 추가
    private void setRootId(Comment comment, Long parentId) {
        if (parentId == null) {
            parentId = comment.getCommentId();
        }
        comment.setParentId(parentId);
    }
}
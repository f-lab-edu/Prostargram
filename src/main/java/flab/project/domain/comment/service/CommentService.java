package flab.project.domain.comment.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.comment.model.Comment;
import flab.project.domain.comment.mapper.CommentMapper;
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

        commentMapper.addComment(comment);

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
        if (parentId != null && parentId <= 0) {
            throw new InvalidUserInputException("Invalid parentId.");
        }
    }
}
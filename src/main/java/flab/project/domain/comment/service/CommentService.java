package flab.project.domain.comment.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.comment.model.Comment;
import flab.project.domain.comment.mapper.CommentMapper;
import flab.project.domain.comment.model.CommentWithUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private static final int MAX_LIMIT = 10;

    private final CommentMapper commentMapper;

    public Comment addComment(long postId, long userId, Long parentId, String content) {
        validateComment(postId, parentId, content);

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        commentMapper.addComment(comment);

        return comment;
    }

    public List<CommentWithUser> getComments(long postId, long userId, Long lastCommentId, long limit) {
            validatePostId(postId);
            validatePagingData(lastCommentId, limit);

            return commentMapper.getComments(postId, userId, lastCommentId, limit);
    }

    private void validateComment(long postId, Long parentId, String content) {
        // Todo userId는 추후 삭제 예정이므로 Service 뿐만 아니라 Service Test 에서 검증 제외
        validatePostId(postId);
        validateParentId(parentId);
        validateContent(content);
    }

    private void validatePostId(long postId) {
        if (postId <= 0) {
            throw new InvalidUserInputException("Invalid postId.");
        }
    }

    private void validateParentId(Long parentId) {
        if (parentId != null && parentId <= 0) {
            throw new InvalidUserInputException("Invalid parentId.");
        }
    }

    private void validateContent(String content) {
        if (StringUtils.isBlank(content) || content.length() > 1000) {
            throw new InvalidUserInputException("Invalid content.");
        }
    }

    private void validatePagingData(Long lastCommentId, long limit) {

        if (lastCommentId != null && lastCommentId <= 0) {
            throw new InvalidUserInputException("Invalid lastCommentId.");
        }

        if (limit <= 0 || limit > MAX_LIMIT) {
            throw new InvalidUserInputException("Invalid limit.");
        }
    }
}
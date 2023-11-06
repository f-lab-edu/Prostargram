package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.CommentWithUser;
import flab.project.data.dto.model.Comment;
import flab.project.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

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

    public List<CommentWithUser> getComments(long postId, long lastCommentId, long limit) {
        validatePostId(postId);
        validatePagingData(lastCommentId, limit);

        return commentMapper.findAllByPostId(postId, lastCommentId, limit);
    }

    private void validateComment(long postId, Long parentId, String content) {
        // Todo userId는 추후 삭제 예정이므로 로직에서 제외
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
        if (content == null || content.length() == 0 || content.length() > 1000) {
            throw new InvalidUserInputException("Invalid content.");
        }
    }

    private void validatePagingData(long lastCommentId, long limit) {
        if (lastCommentId <= 0) {
            throw new InvalidUserInputException("Invalid lastCommentId.");
        }

        if (limit <= 0) {
            throw new InvalidUserInputException("Invalid limit.");
        }
    }
}
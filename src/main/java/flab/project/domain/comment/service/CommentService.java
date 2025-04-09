package flab.project.domain.comment.service;

import flab.project.common.model.PaginationModel;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.comment.exception.CommentNotFoundException;
import flab.project.domain.comment.exception.PostNotFoundException;
import flab.project.domain.comment.model.Comment;
import flab.project.domain.comment.mapper.CommentMapper;
import flab.project.domain.comment.model.CommentWithUser;
import flab.project.domain.post.mapper.PostMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private static final int MAX_LIMIT = 10;

    private final CommentMapper commentMapper;
    private final PostMapper postMapper;

    @Transactional
    public Comment addComment(long postId, long userId, Long parentId, String content) {
        validateComment(postId, parentId, content);

        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        commentMapper.addComment(comment);
        postMapper.addComment(postId);

        return comment;
    }

    @Transactional
    public Comment addDebateComment(long postId, long optionId, long userId, Long parentId, String content) {
        validateComment(postId, parentId, content);

        Comment comment = Comment.builder()
                .postId(postId)
                .optionId(optionId)
                .userId(userId)
                .parentId(parentId)
                .content(content)
                .build();

        commentMapper.addComment(comment);
        postMapper.addComment(postId);

        return comment;
    }

    public PaginationModel<List<CommentWithUser>> getComments(
            long postId,
            long userId,
            Optional<Long> parentId,
            Long lastCommentId,
            long limit
    ) {
        validatePostId(postId);
        validateParentId(parentId);
        validatePagingData(lastCommentId, limit);

        List<CommentWithUser> comments;

        if (parentId.isEmpty()) {
            comments = commentMapper.getComments(postId, userId, lastCommentId, limit + 1);
        } else {
            comments = commentMapper.getChildComments(postId, userId, parentId.get(), lastCommentId, limit + 1);
        }

        boolean hasNext = comments.size() > limit;
        int subListEndIndex = Math.min(comments.size(), (int) limit);
        return new PaginationModel<>(comments.subList(0, subListEndIndex), hasNext);
    }

    public PaginationModel<List<CommentWithUser>> getDebateComments(
            long postId,
            long optionId,
            Long userId,
            Optional<Long> parentId,
            Long lastCommentId,
            long limit
    ) {
        validatePostId(postId);
        validateParentId(parentId);
        validatePagingData(lastCommentId, limit);

        List<CommentWithUser> comments;

        if (parentId.isEmpty()) {
            comments = commentMapper.getDebateComments(postId, optionId, userId, lastCommentId, limit + 1);
        } else {
            comments = commentMapper.getChildDebateComments(postId, optionId, userId, parentId.get(), lastCommentId,
                    limit + 1);
        }

        boolean hasNext = comments.size() > limit;
        int subListEndIndex = Math.min(comments.size(), (int) limit);
        return new PaginationModel<>(comments.subList(0, subListEndIndex), hasNext);
    }

    private void validateComment(long postId, Long parentId, String content) {
        validatePostId(postId);
        validateParentId(parentId);
        validateContent(content);
    }

    private void validatePostId(long postId) {
        if (postId <= 0) {
            throw new InvalidUserInputException("Invalid postId.");
        }

        boolean existsPost = postMapper.existsById(postId);
        if (!existsPost) {
            throw new PostNotFoundException();
        }
    }

    private void validateParentId(Long parentId) {
        if (parentId == null) {
            return;
        }

        if (parentId <= 0) {
            throw new InvalidUserInputException("Invalid parentId.");
        }

        boolean existsParent = commentMapper.exists(parentId);
        if (!existsParent) {
            throw new CommentNotFoundException();
        }
    }

    private void validateParentId(Optional<Long> parentId) {
        if (parentId.isEmpty()) {
            return;
        }

        if (parentId.get() <= 0) {
            throw new InvalidUserInputException("Invalid parentId.");
        }

        boolean existsParent = commentMapper.exists(parentId.get());
        if (!existsParent) {
            throw new CommentNotFoundException();
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
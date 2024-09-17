package flab.project.domain.like.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.like.mapper.CommentLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentLikeService {

    private final CommentLikeMapper commentLikeMapper;

    public void addCommentLike(long commentId, Long userId) {
        checkCommentIdAndUserId(commentId, userId);
        commentLikeMapper.addCommentLike(commentId, userId);
    }

    public void cancelCommentLike(long commentId, Long userId) {
        checkCommentIdAndUserId(commentId, userId);
        commentLikeMapper.cancelCommentLike(commentId, userId);
    }

    private void checkCommentIdAndUserId(long commentId, Long userId) {
        if (commentId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid commentId or userId");
        }
    }
}

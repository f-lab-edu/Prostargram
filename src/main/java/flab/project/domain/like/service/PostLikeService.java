package flab.project.domain.like.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NotFoundException;
import flab.project.domain.like.mapper.PostLikeMapper;
import flab.project.domain.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeMapper postLikeMapper;
    private final PostMapper postMapper;

    public SuccessResponse<Void> addPostLike(long postId, long userId) {
        checkPostIdAndUserId(postId, userId);
        postLikeMapper.addPostLike(postId, userId);

        return new SuccessResponse<>();
    }

    public SuccessResponse<Void> cancelPostLike(long postId, Long userId) {
        checkPostIdAndUserId(postId, userId);
        boolean hasPost = postMapper.existsById(postId);
        if (!hasPost) {
            throw new NotFoundException("존재하지 않는 게시물입니다.");
        }

        boolean hasLike = postLikeMapper.hasLike(postId, userId);
        if (!hasLike) {
            throw new NotFoundException("좋아요를 하지 않은 게시물입니다.");
        }

        postLikeMapper.cancelLike(postId, userId);
        return new SuccessResponse<>();
    }

    private void checkPostIdAndUserId(long postId, long userId) {
        if (postId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid postId or userId");
        }
    }
}
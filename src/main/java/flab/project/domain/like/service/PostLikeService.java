package flab.project.domain.like.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.like.mapper.PostLikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeMapper postLikeMapper;

    private void checkPostIdAndUserId(long postId, long userId) {
        if (postId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid postId or userId");
        }
    }

    public SuccessResponse<Void> addPostLike(long postId, long userId) {
        checkPostIdAndUserId(postId, userId);
        postLikeMapper.addPostLike(postId, userId);

        return new SuccessResponse<>();
    }
}
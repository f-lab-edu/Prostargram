package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeMapper likeMapper;

    private void checkPostIdAndUserId(long postId, long userId) {
        if (postId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid postId or userId");
        }
    }

    public SuccessResponse addPostLike(long postId, long userId) {
        checkPostIdAndUserId(postId, userId);
        likeMapper.addPostLike(postId, userId);
        return new SuccessResponse();
    }
}
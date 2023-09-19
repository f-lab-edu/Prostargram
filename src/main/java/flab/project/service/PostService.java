package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.DeletedPostException;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.PostWithUser;
import flab.project.data.dto.model.*;
import flab.project.data.enums.PostType;
import flab.project.mapper.PostMapper;
import flab.project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;

    public SuccessResponse<PostWithUser> getPostDetail(long postId, long userId, PostType postType) {
        validateGetPostDetail(postId, userId);

        BasicUser basicUser = userMapper.getBasicUser(userId);
        BasePost post = getPostDetailUsingPostType(postId, userId, postType);

        if (post == null) {
            throw new DeletedPostException();
        }

        PostWithUser postWithUser = new PostWithUser(post, basicUser);

        return new SuccessResponse<>(postWithUser);
    }

    private void validateGetPostDetail(long postId, long userId) {
        validatePostId(postId);
        validateUserId(userId);
    }

    private void validatePostId(long postId) {
        if (postId <= 0) {
            throw new InvalidUserInputException();
        }
    }

    private void validateUserId(long userId) {
        if (userId <= 0) {
            throw new InvalidUserInputException();
        }
    }

    private BasePost getPostDetailUsingPostType(long postId, long userId, PostType postType) {
        return switch (postType) {
            case BASIC -> postMapper.getBasicPostDetail(postId, userId);
            case POLL -> postMapper.getPollPostDetail(postId, userId);
            case DEBATE -> postMapper.getDebatePostDetail(postId, userId);
        };
    }
}
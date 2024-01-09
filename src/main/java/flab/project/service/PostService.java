package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.AddPostRequest;
import flab.project.mapper.PostMapper;
import flab.project.utils.PostRedisUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.PostWithUser;
import flab.project.data.dto.model.*;
import flab.project.data.enums.PostType;
import flab.project.mapper.UserMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final PostRedisUtil postRedisUtil;

    public void addPost(long userId, AddPostRequest post) {
        validateUserId(userId);

        postMapper.save(userId, post);
    }

    public SuccessResponse<PostWithUser> getPostDetail(long postId, long userId, PostType postType)
            throws NotFoundException {
        validateGetPostDetail(postId, userId);

        BasicUser basicUser = userMapper.getBasicUser(userId);
        BasePost post = getPostDetailUsingPostType(postId, userId, postType);

        if (post == null) {
            throw new NotFoundException("post not found");
        }

        PostWithUser postWithUser = new PostWithUser(post, basicUser);

        return new SuccessResponse<>(postWithUser);
    }

    public List<BasePost> getPostsByPostIds(List<Long> postIds) {
        List<BasePost> feeds = postRedisUtil.getFeeds(postIds);

        List<Long> postIdsNotInRedis = extractPostIdsNotInRedis(feeds, postIds);
        if (!postIdsNotInRedis.isEmpty()) {
            List<BasePost> postsInDB = postMapper.findByPostIdIn(postIdsNotInRedis);
            feeds.addAll(postsInDB);
        }

        return feeds;
    }

    //todo 이름을 어떻게 지어야할까?
    private List<Long> extractPostIdsNotInRedis(List<BasePost> feeds, List<Long> postIds) {
        List<Long> postIdsNotInRedis = new ArrayList<>();

        for (int index = 0; index < feeds.size(); index++) {
            BasePost post = feeds.get(index);
            if (post == null) {
                postIdsNotInRedis.add(postIds.get(index));
            }
        }

        return postIdsNotInRedis;
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
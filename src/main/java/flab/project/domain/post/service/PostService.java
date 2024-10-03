package flab.project.domain.post.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NotFoundException;
import flab.project.domain.post.model.AddPostRequest;
import flab.project.domain.post.model.*;
import flab.project.domain.post.mapper.PostMapper;
import flab.project.domain.user.model.BasicUser;
import flab.project.utils.PostRedisUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.post.enums.PostType;
import flab.project.domain.user.mapper.UserMapper;
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

    public SuccessResponse<PostWithUser> getPostDetail(long postId, long userId, PostType postType) {
        validateGetPostDetail(postId, userId);

        BasicUser basicUser = userMapper.getBasicUser(userId);
        BasePost post = getPostDetailUsingPostType(postId, userId, postType);

        if (post == null) {
            throw new NotFoundException("post not found.");
        }

        PostWithUser postWithUser = new PostWithUser(post, basicUser);

        return new SuccessResponse<>(postWithUser);
    }

    public List<BasePost> lookAsidePosts(List<Long> postIds, long userId) {
        List<BasePost> posts = new ArrayList<>(postRedisUtil.getFeeds(postIds));

        List<Long> postIdsNotInRedis = extractPostIdsNotInRedis(posts, postIds);
        if (!postIdsNotInRedis.isEmpty()) {
            List<BasePost> postsNotInRedis = getPostsFromDb(userId, postIdsNotInRedis);

            postRedisUtil.saveAll(postsNotInRedis);
            posts.addAll(postsNotInRedis);
        }

        return posts.stream().filter(Objects::nonNull).toList();
    }

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

    private List<BasePost> getPostsFromDb(long userId,  List<Long> postIdsNotInRedis) {
        Map<Long, PostType> postIdPostTypeMap = generatePostIdPostTypeMap(postIdsNotInRedis);

        List<BasicPost> basicPosts = getBasicPostsFromDb(userId, postIdPostTypeMap);
        List<DebatePost> debatePosts = getDebatePostsFromDb(userId, postIdPostTypeMap);

        return Stream.concat(basicPosts.stream(), debatePosts.stream())
            .toList();
    }

    private Map<Long, PostType> generatePostIdPostTypeMap(List<Long> postIdsNotInRedis) {
        List<PostTypeModel> postTypeModels = postMapper.findTypeByPostIds(postIdsNotInRedis);

        return postTypeModels.stream()
            .collect(Collectors.toMap(
                PostTypeModel::getPostId,
                PostTypeModel::getPostType
            ));
    }

    private List<BasicPost> getBasicPostsFromDb(long userId, Map<Long, PostType> postIdPostTypeMap) {
        List<Long> basicPostIds = extractBasicPostIds(postIdPostTypeMap);
        if(basicPostIds.isEmpty()){
            return List.of();
        }

        return postMapper.getBasicPostsWhereIn(basicPostIds, userId);
    }

    private List<DebatePost> getDebatePostsFromDb(long userId, Map<Long, PostType> postIdPostTypeMap) {
        List<Long> debatePostIds = extractDebatePostIds(postIdPostTypeMap);
        if(debatePostIds.isEmpty()){
            return List.of();
        }

        return postMapper.getDebatePostsWhereIn(debatePostIds, userId);
    }

    private List<Long> extractDebatePostIds(Map<Long, PostType> postIdPostTypeMap) {
        return postIdPostTypeMap
            .keySet()
            .stream()
            .filter(postId -> postIdPostTypeMap.get(postId) == PostType.DEBATE)
            .toList();
    }

    private List<Long> extractBasicPostIds(Map<Long, PostType> postIdPostTypeMap) {
        return postIdPostTypeMap
            .keySet()
            .stream()
            .filter(postId -> postIdPostTypeMap.get(postId) == PostType.BASIC)
            .toList();
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
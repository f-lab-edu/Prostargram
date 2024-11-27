package flab.project.domain.like.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NotFoundException;
import flab.project.domain.like.mapper.PostLikeMapper;
import flab.project.domain.like.model.PostLike;
import flab.project.domain.post.mapper.PostMapper;
import flab.project.utils.PostRedisUtil;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeMapper postLikeMapper;
    private final PostMapper postMapper;
    private final PostRedisUtil postRedisUtil;

    public SuccessResponse<Void> addPostLike(long postId, long userId) {
        checkPostIdAndUserId(postId, userId);
        postLikeMapper.addPostLike(postId, userId);
        postMapper.addPostLike(postId);

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
        postMapper.cancelLike(postId);

        return new SuccessResponse<>();
    }

    public Map<Long, Boolean> hasPostLike(List<Long> postIds, long userId) {
        Set<Long> postsHavingLike = postLikeMapper.getPostsHavingLike(postIds, userId);

        return postIds.stream()
                .collect(Collectors.toMap(
                        postId -> postId,
                        postsHavingLike::contains
                ));
    }

    public Map<Long, Long> getPostLikeCount(List<Long> postIds) {
        List<PostLike> postLikeCount = postLikeMapper.getPostLikeCount(postIds);

        return postLikeCount.stream()
                .collect(Collectors.toMap(
                        PostLike::getPostId,
                        PostLike::getLikeCount
                ));
    }

    private void checkPostIdAndUserId(long postId, long userId) {
        if (postId <= 0 || userId <= 0) {
            throw new InvalidUserInputException("Invalid postId or userId");
        }
    }
}
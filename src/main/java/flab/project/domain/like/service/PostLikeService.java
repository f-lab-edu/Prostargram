package flab.project.domain.like.service;

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

    public void addPostLike(long postId, long userId) {
        validateExistsPost(postId);

        boolean hasLike = postLikeMapper.hasLike(postId, userId);
        if (hasLike) {
            throw new RuntimeException("이미 좋아요 한 게시물입니다.");
        }

        postLikeMapper.like(postId, userId);
        postMapper.like(postId);
    }

    public void cancelPostLike(long postId, Long userId) {
        validateExistsPost(postId);

        boolean hasLike = postLikeMapper.hasLike(postId, userId);
        if (!hasLike) {
            throw new NotFoundException("좋아요를 하지 않은 게시물입니다.");
        }

        postLikeMapper.cancelLike(postId, userId);
        postMapper.cancelLike(postId);
    }

    private void validateExistsPost(long postId) {
        boolean hasPost = postMapper.existsById(postId);
        if (!hasPost) {
            throw new NotFoundException("존재하지 않는 게시물입니다.");
        }
    }
}
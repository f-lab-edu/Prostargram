package flab.project.domain.user.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.exception.AlreadyFollowException;
import flab.project.domain.user.model.Follows;
import flab.project.domain.user.model.User;
import flab.project.domain.user.enums.GetFollowsType;
import flab.project.domain.user.mapper.FollowMapper;

import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowMapper followMapper;

    public List<User> getFollows(Long userId, GetFollowsType requestType) {
        validateUserIdPositive(userId);

        return followMapper.findAll(userId, requestType);
    }

    @Transactional
    public void addFollow(Follows follows) {
        validateFromUserIdAndToUserIdSame(follows);

        boolean isFollow = followMapper.findByFromUserIdAndToUserId(follows);
        if (isFollow) {
            throw new AlreadyFollowException("이미 팔로우한 상태입니다.");
        }

        followMapper.addFollow(follows);
    }

    @Transactional
    public void deleteFollow(Follows follows) {
        validateFromUserIdAndToUserIdSame(follows);

        boolean isFollow = followMapper.findByFromUserIdAndToUserId(follows);
        if (!isFollow) {
            throw new AlreadyFollowException("팔로우 되어있지 않은 상대입니다.");
        }

        followMapper.deleteFollow(follows);
    }

    private void validateUserIdPositive(Long userId) {
        if (userId <= 0) {
            throw new InvalidUserInputException("userId는 양수여야 합니다.");
        }
    }

    private void validateFromUserIdAndToUserIdSame(Follows follows) {
        if (follows.getFromUserId() == follows.getToUserId()) {
            throw new InvalidUserInputException("fromUserId와 toUserId는 같을 수 없습니다.");
        }
    }

    public Map<Long, Boolean> isFollows(List<Long> postIds, long userId) {
        Set<Long> postsHavingLike = followMapper.getPostsHavingFollow(postIds, userId);

        return postIds.stream()
                .collect(Collectors.toMap(
                        postId -> postId,
                        postsHavingLike::contains
                ));
    }
}
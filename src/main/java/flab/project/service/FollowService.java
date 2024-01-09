package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Follows;
import flab.project.data.dto.model.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.mapper.FollowMapper;

import java.util.List;

import flab.project.utils.FollowerRedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowMapper followMapper;
    private final FollowerRedisUtil followerRedisUtil;

    public SuccessResponse<List<User>> getFollows(Long userId, GetFollowsType requestType) {
        validateUserIdPositive(userId);

        List<User> result = followMapper.findAll(userId, requestType);

        return new SuccessResponse<>(result);
    }

    public List<Long> getFollowerIds(Long userId) {
        validateUserIdPositive(userId);

        List<Long> followerIds = followerRedisUtil.getFollowerIds(userId);
        if (followerIds.isEmpty()) {
            followerIds = followMapper.findAllFollowerIds(userId);
        }

        return followerIds;
    }

    public SuccessResponse addFollow(Follows follows) {
        validateFromUserIdAndToUserIdSame(follows);

        followMapper.addFollow(follows);

        return new SuccessResponse();
    }

    public SuccessResponse deleteFollow(Follows follows) {
        validateFromUserIdAndToUserIdSame(follows);

        followMapper.deleteFollow(follows);

        return new SuccessResponse<>();
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
}
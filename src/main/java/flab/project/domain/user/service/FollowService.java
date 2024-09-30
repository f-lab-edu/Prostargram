package flab.project.domain.user.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.model.Follows;
import flab.project.domain.user.model.User;
import flab.project.domain.user.enums.GetFollowsType;
import flab.project.domain.user.mapper.FollowMapper;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowMapper followMapper;

    public List<User> getFollows(Long userId, GetFollowsType requestType) {
        validateUserIdPositive(userId);

        return followMapper.findAll(userId, requestType);
    }

    public void addFollow(Follows follows) {
        validateFromUserIdAndToUserIdSame(follows);

        followMapper.addFollow(follows);
    }

    public void deleteFollow(Follows follows) {
        validateFromUserIdAndToUserIdSame(follows);

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
}
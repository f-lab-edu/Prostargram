package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Follows;
import flab.project.data.dto.model.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.mapper.FollowMapper;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowMapper followMapper;

    public SuccessResponse<List<User>> getFollows(Long userId, GetFollowsType requestType) {
        userIdValidation(userId);

        List<User> result = followMapper.findAll(userId,requestType);

        return new SuccessResponse<>(result);
    }

    private void userIdValidation(Long userId) {
        if (userId <= 0) {
            throw new InvalidUserInputException("userId는 양수여야 합니다.");
        }
    }

    public SuccessResponse addFollow(Follows follows) {
        try {
            checkFromUserIdAndToUserIdSame(follows);

            followMapper.addFollow(follows);

            return new SuccessResponse();
        } catch (InvalidUserInputException e) {
            throw new InvalidUserInputException("fromUserId와 toUserId는 같을 수 없습니다.");
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            throw new DuplicateKeyException("중복 요청으로 인해 나타난 에러입니다.");
        } catch (DataIntegrityViolationException e) {
            throw new InvalidUserInputException("Foreign Key 문제로 발생한 에러입니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("SERVER 에러 입니다.");
        }
    }

    private void checkFromUserIdAndToUserIdSame(Follows follows) {
        if (follows.getFromUserId() == follows.getToUserId()) {
            throw new InvalidUserInputException();
        }
    }

    public SuccessResponse deleteFollow(Follows follows) {
        try {
            checkFromUserIdAndToUserIdSame(follows);

            followMapper.deleteFollow(follows);

            return new SuccessResponse<>();
        } catch (InvalidUserInputException e) {
            e.printStackTrace();
            throw new InvalidUserInputException("fromUserId와 toUserId는 같을 수 없습니다.");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

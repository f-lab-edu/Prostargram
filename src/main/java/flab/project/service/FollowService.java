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
        List<User> result = followMapper.findAll(userId,requestType);

        return new SuccessResponse<>(result);
    }

    public SuccessResponse addFollow(Follows follows) {
        try {
            checkFromUserIdAndToUserIdSame(follows);

            followMapper.addFollow(follows);

            return new SuccessResponse();
        } catch (InvalidUserInputException e) {
            e.printStackTrace();
            throw new InvalidUserInputException("fromUserId와 toUserId는 같을 수 없습니다.");
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            throw new DuplicateKeyException("중복 요청으로 인해 나타난 에러입니다.");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            // todo 인자로 반드시 message를 받지만 사실 이 message를 쓸 일이 없다.
            //  우리 서버 구조에서는 ResponseEnum을 미리 구축해놓고 적절한 Enum을 이용해 FailResponse를 만들어 던지기만 하기 때문이다.
            //  이건 Spring이 의도한 방식의 처리 구조가 아니라서 이런일이 발생하는걸까? 구조를 바꿔야하나? 왜그러지?
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

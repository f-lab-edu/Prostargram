package flab.project.service;

import flab.project.config.baseresponse.BaseResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.FollowRequestDto;
import flab.project.data.dto.User;
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
        List<User> result = followMapper.findAll(requestType, userId);

        return new SuccessResponse<>(result);
    }

    public SuccessResponse postFollow(FollowRequestDto followRequestDto) {
        try {
            followRequestDto.checkFromUserIdAndToUserIdSame();

            followMapper.postFollow(followRequestDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } catch (DuplicateKeyException e) {
            e.printStackTrace(); //todo 예외 발생했을 때, 로깅 라이브러리 사용해서 로그 남기기.
            throw new DuplicateKeyException("서버에서 처리중입니다. 잠시 기다려주세요");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new DataIntegrityViolationException("서버에서 처리중입니다. 잠시 기다려주세요");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB 에러 입니다.");
        }

        return new SuccessResponse();
    }

}

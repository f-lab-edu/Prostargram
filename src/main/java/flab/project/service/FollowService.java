package flab.project.service;

import flab.project.config.baseresponse.BaseResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.FollowRequestDto;
import flab.project.data.dto.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.mapper.FollowMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowMapper followMapper;

    public BaseResponse<List<User>> getFollows(Long userId, GetFollowsType requestType) {
        List<User> result = followMapper.findAll(requestType, userId);

        return new SuccessResponse<>(result);
    }

    public BaseResponse postFollow(FollowRequestDto followRequestDto) {
        try {
            followMapper.postFollow(followRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DB 에러 입니다.");
        }

        return new SuccessResponse();
    }
}

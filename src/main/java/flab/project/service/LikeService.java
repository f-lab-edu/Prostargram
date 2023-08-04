package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeMapper likeMapper;

    public SuccessResponse addPostLike(long postId, long userId) {
        try {
            likeMapper.addPostLike(postId, userId);
            return new SuccessResponse();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("서버 런타임 에러 입니다.");
        }
    }
}

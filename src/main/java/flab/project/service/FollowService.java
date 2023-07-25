package flab.project.service;

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

    public List<User> getFollows(Long userId, GetFollowsType requestType) {
        List<User> result = followMapper.findAll(requestType, userId);

        return result;
    }
}

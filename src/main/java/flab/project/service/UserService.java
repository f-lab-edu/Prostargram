package flab.project.service;

import flab.project.data.dto.model.Profile;
import flab.project.data.dto.model.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.mapper.HashtagMapper;
import flab.project.mapper.HyperLinkMapper;
import flab.project.mapper.IconMapper;
import flab.project.mapper.InterestMapper;
import flab.project.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashtagMapper hashtagMapper;
    private final HyperLinkMapper hyperLinkMapper;
    private final InterestMapper interestMapper;
    private final IconMapper iconMapper;

    public List<User> getFollows(Long userId, GetFollowsType requestType) {
        List<User> result = userMapper.findAll(requestType, userId);

        return result;
    }

    public void updateUserTable(long userId, Profile updateProfileDto) {
        if (updateProfileDto.hasProfileField()) {
            userMapper.updateProfile(userId, updateProfileDto);
        }
    }


}

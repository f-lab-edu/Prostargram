package flab.project.service;

import flab.project.data.dto.User;
import flab.project.data.entity.Follows;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;

}

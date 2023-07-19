package flab.project.controller;

import flab.project.data.dto.*;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "UserController")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "팔로워/팔로잉 목록 확인하기 API"
    )
    @Parameters(
            value = {
                    @Parameter(name = "userId", description = "프로필을 확인하고자 하는 유저의 id (로그인한 유저 아님)", required = true),
                    @Parameter(name = "type", description = "팔로워/팔로잉 페이지에 따라 파라미터를 다르게 전달해야함.", required = true)
            }
    )
    @GetMapping(value = "/users/{userId}/follows")
    public List<User> getFollows(
            @PathVariable("userId") Long userId,
            @RequestParam("type") GetFollowsType type
    ) {
        return userService.getFollows(userId,type);
    }
}
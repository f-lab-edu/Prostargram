package flab.project.controller;

import flab.project.config.baseresponse.BaseResponse;
import flab.project.data.dto.FollowRequestDto;
import flab.project.data.dto.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowController {


    private final FollowService followService;
    @Operation(
        summary = "팔로워 목록 확인하기 API"
    )
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "팔로워목록을 확인하고자 하는 유저의 id (로그인한 유저 아님)", required = true),
        }
    )
    @GetMapping(value = "/users/{userId}/follows")
    public List<User> getFollowers(
        @PathVariable("userId") Long userId
    ) {
        return followService.getFollows(userId, GetFollowsType.FOLLOWERS);
    }

    @Operation(
        summary = "팔로잉 목록 확인하기 API"
    )
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "팔로워목록을 확인하고자 하는 유저의 id (로그인한 유저 아님)", required = true),
        }
    )
    @GetMapping(value = "/users/{userId}/followings")
    public List<User> getFollowings(
        @PathVariable("userId") Long userId
    ) {
        return followService.getFollows(userId, GetFollowsType.FOLLOWINGS);
    }

    @Operation(
        summary = "팔로워/팔로잉 생성 API"
    )
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "로그인한 유저의 id", required = true),
        }
    )
    @PostMapping(value = "/users/{userId}/follows")
    public BaseResponse postFollow(
        @RequestBody FollowRequestDto followRequestDto
    ) {
        return followService.postFollow(followRequestDto);
    }


    @Operation(
        summary = "팔로워/팔로잉 삭제 API"
    )
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "로그인한 유저의 id", required = true),
        }
    )
    @DeleteMapping(value = "/users/{userId}/follows")
    public String deleteFollow(
        FollowRequestDto followRequestDto
    ) {
        return "test";
    }
}

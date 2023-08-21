package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.*;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.data.enums.requestparam.PutFollowType;

import flab.project.service.UserService;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

  
    
    @Operation(
            summary = "프로필 수정 페이지 정보 확인하기 API"
    )
    @GetMapping(value = "/users/{userId}/profile_update_page")
    public SuccessResponse getProfilePageInfo(
        @PathVariable("userId") long userId
    ) {
        return userService.getProfileInfo(userId,GetProfileRequestType.UPDATE);
    }

    @Operation(
            summary = "프로필 페이지 정보 확인하기 API"
    )
    @GetMapping(value = "/users/{userId}/profile_page")
    public SuccessResponse<Profile> getProfileUpdatePageInfo(
            @PathVariable("userId") long userId
    ) {
        return userService.getProfileInfo(userId, GetProfileRequestType.GET);
    }
    
    @Operation(
            summary = "개인 설정 상태 확인하기 API"
    )
    @Parameters(
            value = {
                @Parameter(name = "userId", description = "설정 상태를 확인하고자 하는 유저의 id", required = true),
            }
    )
    @GetMapping(value = "/users/{userId}/options")
    public GetOptionsResponseDto getPersonalSettings(
        @PathVariable("userId") Long userId
    ) {
        return null;
    }

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
        return userService.getFollows(userId, GetFollowsType.FOLLOWER);
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
        return userService.getFollows(userId, GetFollowsType.FOLLOWING);
    }
  
    @Operation(
        summary = "옵션을 수정하는 API"
    )
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "설정 상태를 변경하고자 하는 유저의 id", required = true),
        }
    )
    @PatchMapping(value = "/users/{userId}/options")
    public String updatePersonalSettings(
        @PathVariable("userId") Long userId,
        PatchOptionsRequestDto updateOptionsRequestDto
    ) {
        return "test";
    }

    @Operation(
        summary = "프로필 수정하기 API"
    )
    @PatchMapping(value = "/users/profile-info")
    public String updateProfile(
        Profile updateProfileRequestDto
    ) {
        return "test";
    }

    @Operation(
        summary = "팔로워/팔로잉 생성/삭제 API"
    )
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "로그인한 유저의 id", required = true),
        }
    )
    @PutMapping(value = "/users/{userId}/follows")
    public String putFollows(
        PutFollowRequestDto putFollowRequestDto,
        PutFollowType putFollowRequestType
    ) {
        return "test";
    }

}

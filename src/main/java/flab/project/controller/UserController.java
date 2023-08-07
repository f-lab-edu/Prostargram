package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.*;
import flab.project.data.dto.model.Profile;
import flab.project.data.dto.model.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.data.enums.requestparam.PutFollowType;

import flab.project.service.UserService;
import flab.project.facade.UserFacade;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.context.aot.AbstractAotProcessor.Settings;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;


    @Operation(
        summary = "프로필 정보 확인하기 API",
        description = "1. 프로필 페이지에서 사용되는 API ( type으로 PROFILE_PAGE_REQUEST전달 ), "
            + "2. 프로필 수정하기 페이지 에서 사용되는 API ( type으로 UPDATE_PAGE_REQUEST전달 ) ")
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "프로필을 확인하고자 하는 유저의 id (로그인한 유저 아님)", required = true),
            @Parameter(name = "type", description = "어떤 화면에서 사용하느냐에 따라 type을 다르게 전달해야함.", required = true)
        }
    )
    @ApiResponses({
        @ApiResponse(description =
            "PROFILE_PAGE_REQUEST로 요청할 경우와 UPDATE_PAGE_REQUEST로 요청할 경우 반환 형식이 다름."
                + " example에는 공통 필드만 표시되므로 직접 실행해서 확인바람.")})
    @GetMapping(value = "/users/{userId}")
    public Profile getProfileInfo(
        @PathVariable("userId") Long userId,
        @RequestParam("type") GetProfileRequestType type
    ) {
        return null;
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
    public Settings getPersonalSettings(
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
        Settings updateOptionsRequestDto
    ) {
        return "test";
    }

    @Operation(
        summary = "프로필 수정하기 API"
    )
    @PatchMapping(value = "/users/{userId}/profile-info")
    public SuccessResponse updateProfile(
        @Validated @Positive @PathVariable long userId,
        @Validated @RequestBody Profile updateProfileDto
        //todo interests에는 #을 붙여서 전송해줘야 한다는 내용을 Swagger에 추가해줘야함.
    ) {
        return userFacade.updateProfile(userId, updateProfileDto);
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

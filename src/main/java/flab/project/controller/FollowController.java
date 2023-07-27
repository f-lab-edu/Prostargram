package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.Follows;
import flab.project.data.dto.model.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
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
    @GetMapping(value = "/users/{userId}/followers")
    public SuccessResponse<List<User>> getFollowers(
        @PathVariable("userId") @Positive Long userId
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
    public SuccessResponse<List<User>> getFollowings(
        @PathVariable("userId") @Positive Long userId
    ) {
        return followService.getFollows(userId, GetFollowsType.FOLLOWINGS);
    }

    @Operation(
        summary = "팔로워/팔로잉 한번에 불러오는 API",
        description = "메인페이지의 피드위 스토리 위치에 표시되는 팔로워/팔로잉 목록을 위해 사용된다."
    )
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "팔로워목록을 확인하고자 하는 유저의 id (로그인한 유저 아님)", required = true),
        }
    )
    @GetMapping(value = "/users/{userId}/follows/all")
    public SuccessResponse<List<User>> getAllFollows(
        @PathVariable("userId") @Positive Long userId
    ) {
        return followService.getFollows(userId, GetFollowsType.ALL);
    }

    @Operation(
        summary = "팔로워/팔로잉 생성 API"
    )
    @Parameters(
        value = {
            @Parameter(name = "userId", description = "로그인한 유저의 id", required = true),
        }
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class)
                )
            }
        ),
    })
    @PostMapping(value = "/users/{userId}/follows")
    public SuccessResponse postFollow(
        @Valid @RequestBody Follows follows
    ) {
        return followService.postFollow(follows);
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
        Follows follows
    ) {
        return "test";
    }
}

package flab.project.domain.user.controller;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.user.model.Follows;
import flab.project.domain.user.model.User;
import flab.project.domain.user.enums.GetFollowsType;
import flab.project.domain.user.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "팔로워 / 팔로잉 API")
@Validated
@RequiredArgsConstructor
@RestController
public class FollowController {

    private final FollowService followService;

    @Operation(
            summary = "팔로워 목록 확인하기 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "팔로워 목록 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4000,
                                                "message": "올바르지 않은 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인하지 않은 유저가 요청을 보낸 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4006,
                                                "message": "로그인이 필요합니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저의 팔로워 목록을 조회하는 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4001,
                                                "message": "존재하지 않는 유저에 대한 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 5000,
                                                "message": "서버 오류입니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping(value = "/users/{userId}/followers")
    public SuccessResponse<List<User>> getFollowers(
            @PathVariable("userId") @Positive Long userId
    ) {
        return followService.getFollows(userId, GetFollowsType.FOLLOWERS);
    }

    @Operation(
            summary = "팔로잉 목록 확인하기 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "팔로잉 목록 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": 요청에 성공하였습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4000,
                                                "message": "올바르지 않은 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인하지 않은 유저가 요청을 보낸 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4006,
                                                "message": "로그인이 필요합니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저의 팔로잉 목록을 조회하는 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4001,
                                                "message": "존재하지 않는 유저에 대한 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 5000,
                                                "message": "서버 오류입니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping(value = "/users/{userId}/followings")
    public SuccessResponse<List<User>> getFollowings(
            @PathVariable("userId") @Positive Long userId
    ) {
        return followService.getFollows(userId, GetFollowsType.FOLLOWINGS);
    }

    @Operation(
            summary = "팔로워/팔로잉 한번에 불러오는 API",
            description = "메인 페이지의 피드의 스토리 위치에 표시되는 팔로워/팔로잉 목록을 위해 사용된다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "팔로워/팔로잉 목록 조회 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4000,
                                                "message": "올바르지 않은 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인하지 않은 유저가 요청을 보낸 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4006,
                                                "message": "로그인이 필요합니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 5000,
                                                "message": "서버 오류입니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping(value = "/users/{userId}/follows/all")
    public SuccessResponse<List<User>> getAllFollows(
            @PathVariable("userId") @Positive Long userId
    ) {
        return followService.getFollows(userId, GetFollowsType.ALL);
    }

    @Operation(
            summary = "팔로워/팔로잉 생성 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "팔로워/팔로잉 추가 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4000,
                                                "message": "올바르지 않은 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인하지 않은 유저가 요청을 보낸 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4006,
                                                "message": "로그인이 필요합니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인한 유저가 타인의 ID로 팔로워/팔로잉 추가를 요청하는 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4007,
                                                "message": "해당 요청에 대한 권한이 없습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저가 팔로워/팔로잉 추가를 요청받는 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4001,
                                                "message": "존재하지 않는 유저에 대한 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 5000,
                                                "message": "서버 오류입니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping(value = "/users/{userId}/follows")
    public SuccessResponse addFollow(
            @PathVariable("userId") long userId,
            @Valid @RequestBody Follows follows
    ) {
        return followService.addFollow(follows);
    }


    @Operation(
            summary = "팔로워/팔로잉 삭제 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "팔로워/팔로잉 삭제 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4000,
                                                "message": "올바르지 않은 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인하지 않은 유저가 요청을 보낸 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4006,
                                                "message": "로그인이 필요합니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "로그인한 유저가 타인의 ID로 팔로워/팔로잉 삭제를 요청하는 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4007,
                                                "message": "해당 요청에 대한 권한이 없습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 유저가 팔로워/팔로잉 삭제를 요청받는 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4001,
                                                "message": "존재하지 않는 유저에 대한 요청입니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 5000,
                                                "message": "서버 오류입니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @DeleteMapping(value = "/users/{userId}/follows")
    public SuccessResponse deleteFollow(
            @Valid @RequestBody Follows follows
    ) {
        return followService.deleteFollow(follows);
    }
}
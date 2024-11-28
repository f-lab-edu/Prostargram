package flab.project.domain.post.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.post.model.PostWithUser;
import flab.project.domain.post.enums.PostType;
import flab.project.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시물 API")
@Validated
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "일반 게시물 상세 보기 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일반 게시물 상세 보기 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다.",
                                                "result": {
                                                    "post": {
                                                      "postType": "BASIC",
                                                      "postId": 100,
                                                      "userId": 388187,
                                                      "content": "test post content100",
                                                      "hashTagNames": ["testHashTag","testHashTag2"],
                                                      "likeCount": 0,
                                                      "commentCount": 0,
                                                      "createdAt": "2024-10-10T05:47:22.000+00:00",
                                                      "contentImageUrls": ["https://~"],
                                                      "isLike": false,
                                                      "isFollow": false
                                                    },
                                                    "basicUser": {
                                                      "userId": 1,
                                                      "userName": "test1",
                                                      "profileImgUrl": null
                                                    }
                                                  }
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
                    description = "존재하지 않는 일반 게시물을 상세 보기 할 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4002,
                                                "message": "존재하지 않는 일반 게시물입니다."
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
    @GetMapping("/posts/{postId}/basic-post")
    public SuccessResponse<PostWithUser> getBasicPostDetail(
            @LoggedInUserId Long userId,
            @PathVariable("postId") @Positive long postId
    ) {
        return postService.getPostDetail(postId, userId, PostType.BASIC);
    }

    @Operation(
            summary = "토론 게시물 상세 보기 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "토론 게시물 상세 보기 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다."
                                                "result": {
                                                "post": {
                                                  "postType": "DEBATE",
                                                  "postId": 100000001,
                                                  "userId": 100000,
                                                  "content": "이것이 콘텐츠다!",
                                                  "hashTagNames": [
                                                    "#test1"
                                                  ],
                                                  "likeCount": 0,
                                                  "commentCount": 0,
                                                  "createdAt": "2024-10-30T09:58:09.000+00:00",
                                                  "options": [
                                                    {
                                                      "optionId": 2,
                                                      "optionContent": "나는 바보다",
                                                      "voteCount": 0
                                                    },
                                                    {
                                                      "optionId": 1,
                                                      "optionContent": "나는 천재다",
                                                      "voteCount": 0
                                                    }
                                                  ],
                                                  "selectedOptionId": 0,
                                                  "isLike": false,
                                                  "isFollow": false
                                                },
                                                "basicUser": {
                                                  "userId": 100000,
                                                  "userName": "test100000",
                                                  "profileImgUrl": "https://~"
                                                }
                                              }
                                              }
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
                    description = "존재하지 않는 토론 게시물을 상세 보기 할 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4002,
                                                "message": "존재하지 않는 토론 게시물입니다."
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
    @GetMapping("/posts/{postId}/debate-post")
    public SuccessResponse<PostWithUser> getDebatePostDetail(
            @LoggedInUserId Long userId,
            @PathVariable("postId") @Positive long postId
    ) {
        return postService.getPostDetail(postId, userId, PostType.DEBATE);
    }

    @Operation(
            summary = "통계 게시물 상세 보기 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "통계 게시물 상세 보기 성공",
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
                    description = "존재하지 않는 통계 게시물을 상세 보기 할 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FailResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": false,
                                                "code": 4002,
                                                "message": "존재하지 않는 통계 게시물입니다."
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
    @GetMapping("/posts/{postId}/poll-post")
    public SuccessResponse<PostWithUser> getPollPostDetail(
            @LoggedInUserId Long userId,
            @PathVariable("postId") @Positive long postId
    ) {
        return postService.getPostDetail(postId, userId, PostType.POLL);
    }

    @DeleteMapping("/posts/{postId}")
    public SuccessResponse<Void> deletePost(
            @LoggedInUserId Long userId,
            @PathVariable("postId") @Positive long postId
    ) {
        postService.deletePost(userId, postId);

        return new SuccessResponse<>();
    }
}
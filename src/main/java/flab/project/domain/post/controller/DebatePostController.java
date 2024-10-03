package flab.project.domain.post.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.FailedToUpdateProfileImageToDatabaseException;
import flab.project.domain.post.model.AddDebatePostRequest;
import flab.project.domain.post.model.DebatePost;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.template.PostFacadeTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시물 API")
@Validated
@RequiredArgsConstructor
@RestController
public class DebatePostController {

    private final PostFacadeTemplate debatePostFacade;
    private final FanOutService fanOutService;

    @Operation(
            summary = "토론 게시물 작성 API",
            security = @SecurityRequirement(name = "bearer-key")
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시물 작성 성공",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSucces": true,
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
                                                "isSucces": false,
                                                "code": 5000,
                                                "message": "서버 오류입니다."
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/posts/debate")
    public SuccessResponse<DebatePost> addDebatePost(
            @LoggedInUserId Long userId,
            @RequestBody @Validated AddDebatePostRequest debatePost
    ) {
        DebatePost createdPost = (DebatePost) debatePostFacade.addPost(userId, debatePost);

        return new SuccessResponse<>(createdPost);
    }
}
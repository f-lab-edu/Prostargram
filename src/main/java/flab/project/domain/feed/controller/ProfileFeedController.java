package flab.project.domain.feed.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.common.model.PaginationModel;
import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.feed.service.ProfileFeedService;
import flab.project.domain.post.model.PostWithUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "프로필 API")
@Validated
@RestController
@RequiredArgsConstructor
public class ProfileFeedController {

    private final ProfileFeedService profileFeedService;

    @Operation(
            summary = "프로필 피드 조회 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "프로필 피드 조회 성공",
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
                                    // todo Data 넣어야함.
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인 하지 않은 유저가 요청을 보낸 경우",
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
    })
    @GetMapping(value = "/users/{userId}/profile-feeds")
    public SuccessResponse<PaginationModel<List<PostWithUser>>> getProfileFeed(
            @LoggedInUserId Long myUserId,
            @PathVariable("userId") @Positive long userId,
            @RequestParam(required = false) @Positive Long lastPostId
    ) {
        PaginationModel<List<PostWithUser>> profileFeeds = profileFeedService.getFeeds(myUserId, lastPostId);

        return new SuccessResponse<>(profileFeeds);
    }
}


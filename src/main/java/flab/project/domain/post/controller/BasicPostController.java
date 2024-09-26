package flab.project.domain.post.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.post.model.BasicPost;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.template.PostFacadeTemplate;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "게시물 API")
@Validated
@RequiredArgsConstructor
@RestController
public class BasicPostController {

    private final PostFacadeTemplate basicPostFacade;
    private final FanOutService fanOutService;

    @Operation(
            summary = "게시물 작성 API",
            security = @SecurityRequirement(name = "bearer-key") // Bearer 인증 적용
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
                                                "isSuccess": true,
                                                "code": 1000,
                                                "message": "요청에 성공하였습니다."
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "권한이 없는 경우",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "isSuccess": true,
                                                "code": 403,
                                                "message": "권한이 없습니다."
                                            }
                                            """
                            )
                    )

            )
    })
    @PostMapping(
            value = "/posts/basic",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SuccessResponse<BasicPost> addBasicPost(
            @LoggedInUserId Long userId,
            @RequestPart("basicPost") @Validated AddBasicPostRequest basicPost,
            @RequestPart("contentImages") List<MultipartFile> contentImages
    ) {
        basicPost.setContentImages(contentImages);

        BasicPost createdPost = (BasicPost) basicPostFacade.addPost(userId, basicPost);

        return new SuccessResponse<>(createdPost);
    }
}
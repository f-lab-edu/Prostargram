package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "게시물 좋아요 API")
    @Parameters({@Parameter(name = "postId", description = "게시물의 id", required = true)})
    @PostMapping(value = "/posts/{postId}/likes")
    public SuccessResponse addPostLike(@PathVariable("postId") @Positive long postId, @PathVariable("userId") @Positive long userId) {
        return likeService.addPostLike(postId, userId);
    }
}


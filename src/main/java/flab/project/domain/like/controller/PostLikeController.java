package flab.project.domain.like.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.like.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "게시물 좋아요 API")
    @Parameter(name = "postId", description = "게시물의 id", required = true)
    @PostMapping(value = "/posts/{postId}/likes")
    public SuccessResponse<Void> addPostLike(
        @PathVariable("postId") @Positive long postId,
        @LoggedInUserId Long userId
    ) {
        return postLikeService.addPostLike(postId, userId);
    }

    @Operation(summary = "게시물 좋아요 취소 API")
    @Parameter(name = "postId", description = "게시물의 id", required = true)
    @DeleteMapping(value = "/posts/{postId}/likes")
    public SuccessResponse<Void> cancelPostLike(
        @PathVariable("postId") @Positive long postId,
        @LoggedInUserId Long userId
    ) {
        return postLikeService.cancelPostLike(postId, userId);
    }
}
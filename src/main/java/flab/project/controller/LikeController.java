package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "게시물 좋아요 API")
    @Parameters({@Parameter(name = "postId", description = "게시물의 id", required = true)})
    @PostMapping(value = "/posts/{postId}/likes")
    public SuccessResponse addPostLike(@PathVariable("postId") long postId, @PathVariable("userId") long userId) {
        // Todo 로그인 기능(Spring Security)이 구현될 시, userId -> user.getId()로 변경
        return likeService.addPostLike(postId, userId);
    }

    @Operation(summary = "게시물 좋아요 취소 API")
    @Parameters({@Parameter(name = "postId", description = "게시물의 id", required = true)})
    @DeleteMapping(value = "/posts/{postId}/likes")
    public SuccessResponse deletePostLike(@PathVariable("postId") long postId, @PathVariable("userId") long userId) {
        return likeService.deletePostLike(postId, userId);
    }

    @Operation(summary = "게시물 댓글 좋아요 API")
    @Parameters({@Parameter(name = "commentId", description = "댓글의 id", required = true)})
    @PostMapping(value = "/posts/{postId}/likes")
    public SuccessResponse addCommentLike(@PathVariable("commentId") long commentId, @PathVariable("userId") long userId) {
        return likeService.addCommentLike(commentId, userId);
    }

    @Operation(summary = "게시물 댓글 좋아요 취소 API")
    @Parameters({@Parameter(name = "commentId", description = "댓글의 id", required = true)})
    @DeleteMapping(value = "/posts/{postId}/likes")
    public SuccessResponse deleteCommentLike(@PathVariable("commentId") long commentId, @PathVariable("userId") long userId) {
        return likeService.deletePostLike(commentId, userId);
    }
}


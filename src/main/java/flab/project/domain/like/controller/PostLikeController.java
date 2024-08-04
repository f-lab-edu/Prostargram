package flab.project.domain.like.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.like.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(summary = "게시물 좋아요 API")
    @Parameters({@Parameter(name = "postId", description = "게시물의 id", required = true)})
    @PostMapping(value = "/posts/{postId}/likes")
    // Todo JWT 구현 시, userId를 입력으로 받는 내용 제거 예정
    public SuccessResponse<Void> addPostLike(@PathVariable("postId") @Positive long postId, @RequestParam("userId") @Positive long userId) {
        return postLikeService.addPostLike(postId, userId);
    }
}
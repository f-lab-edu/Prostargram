package flab.project.domain.like.controller;


import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.like.service.CommentLikeService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/likes")
    public SuccessResponse<Void> addCommentLike(
            @PathVariable("commentId") @Positive long commentId,
            @LoggedInUserId Long userId
    ) {
        commentLikeService.addCommentLike(commentId, userId);

        return new SuccessResponse<>();
    }
}

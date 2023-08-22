package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.PostType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성 API")
    @Parameters({
            @Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "rootId", description = "최상단 댓글의 id", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "PostType", description = "게시물의 종류", in = ParameterIn.QUERY, required = true)})
    @PostMapping(value = "/posts/{postId}/comment")
    public SuccessResponse addComment(@PathVariable("postId") @Positive long postId, @RequestParam("userId") long userId,
                                      @RequestParam("rootId") long rootId, @RequestParam("PostType") PostType postType) {
        return commentService.addComment(postId, userId, rootId, postType);
    }
}

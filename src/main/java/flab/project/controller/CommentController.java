package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.Comment;
import flab.project.service.CommentService;
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

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성 API")
    @Parameters({
            @Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "content", description = "게시물의 내용", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "rootId", description = "부모 댓글의 id", in = ParameterIn.QUERY, required = true)})
    @PostMapping(value = "/posts/{postId}/comment")
    public SuccessResponse<Comment> addComment(@PathVariable("postId") @Positive long postId, @RequestParam("userId") @Positive long userId, @RequestParam(value = "rootId", required = false) Long rootId, @RequestParam("content") String content) {
        Comment comment = commentService.addComment(postId, userId, rootId, content);
        return new SuccessResponse<>(comment);
    }
}
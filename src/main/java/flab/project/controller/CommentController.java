package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.CommentWithUser;
import flab.project.data.dto.model.Comment;
import flab.project.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성 API")
    @Parameters({@Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "content", description = "게시물의 내용", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "parentId", description = "부모 댓글의 id", in = ParameterIn.QUERY)})
    @PostMapping(value = "/posts/{postId}/comments")
    public SuccessResponse<Comment> addComment(@PathVariable("postId") @Positive long postId,
                                               @RequestParam("userId") @Positive long userId,
                                               @RequestParam(value = "parentId", required = false) @Positive Long parentId,
                                               @RequestBody @NotBlank @Size(min = 1, max = 1000) String content) {
        Comment comment = commentService.addComment(postId, userId, parentId, content);

        return new SuccessResponse<>(comment);
    }

    // Todo 토론 게시물의 경우, 진영을 의미하는 enum 추가 예정
    @Operation(summary = "댓글 가져오기 API")
    @Parameters({@Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "lastCommentId", description = "가장 마지막으로 받아온 댓글의 id", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "limit", description = "한 번에 조회할 댓글의 개수", in = ParameterIn.QUERY, required = true)})
    @GetMapping(value = "posts/{postId}/comments")
    public SuccessResponse<List<CommentWithUser>> getComments(@PathVariable("postId") @Positive long postId,
                                                              @RequestParam(value = "lastCommentId", required = false) @Positive Long lastCommentId,
                                                              @RequestParam(defaultValue = "10") @Positive @Max(10) long limit) {

        List<CommentWithUser> comments = commentService.getComments(postId, lastCommentId, limit);

        return new SuccessResponse<>(comments);
    }
}
package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.CommentWithUser;
import flab.project.data.dto.model.Comment;
import flab.project.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
    @Parameters({
            @Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "content", description = "게시물의 내용", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "parentId", description = "부모 댓글의 id", in = ParameterIn.QUERY)})
    @PostMapping(value = "/posts/{postId}/comments")
    public SuccessResponse<Comment> addComment(@PathVariable("postId") @Positive long postId,
                                               @RequestParam("userId") @Positive long userId,
                                               @RequestParam(value = "parentId", required = false) @Positive Long parentId,
                                               @RequestParam("content") @NotBlank String content) {
        Comment comment = commentService.addComment(postId, userId, parentId, content);

        return new SuccessResponse<>(comment);
    }

    // Todo 토론 게시물의 경우, 진영을 의미하는 enum 추가 예정
    @Operation(summary = "댓글 가져오기 API")
    @Parameters({
            @Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "lastCommentId", description = "가장 마지막으로 받아온 댓글의 id", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "limit", description = "한 번에 조회할 댓글의 개수", in = ParameterIn.QUERY, required = true)})
    @GetMapping(value = "posts/{postId}/comments")
    public SuccessResponse<List<CommentWithUser>> getComment(@PathVariable("postId") @Positive long postId,
                                                             @RequestParam("lastCommentId") @Positive long lastCommentId,
                                                             @RequestParam(defaultValue = "10") @Positive long limit) {
        List<CommentWithUser> comments = commentService.getComment(postId, lastCommentId, limit);

        // Todo 왜 Swagger UI 상에서 response body의 result에 null이 담기지?? CommentWithUser로 매핑이 잘 안 되었나?
        return new SuccessResponse<>(comments);
    }
}
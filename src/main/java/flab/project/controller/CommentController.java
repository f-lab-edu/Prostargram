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

    // Todo 토론 게시물의 경우, 어느 진영(빨간색 or 청색)에 작성할 것인지에 따라 endPoint를 분리할 예정
    // Todo (서버 입장에서) 최상단 댓글의 경우, rootId가 DB에 null이 있는 것이 어색하지 않으므로 회의 후, response에 null을 그대로 반환하는 식으로 수정할 예정
    @Operation(summary = "댓글 작성 API")
    @Parameters({
            @Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "content", description = "게시물의 내용", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "parentId", description = "부모 댓글의 id", in = ParameterIn.QUERY, required = true)})
    @PostMapping(value = "/posts/{postId}/comment")
    public SuccessResponse<Comment> addComment(@PathVariable("postId") @Positive long postId,
                                               @RequestParam("userId") @Positive long userId,
                                               @RequestParam(value = "parentId", required = false) @Positive Long parentId,
                                               @RequestParam("content") String content) {
        Comment comment = commentService.addComment(postId, userId, parentId, content);
        return new SuccessResponse<>(comment);
    }
}
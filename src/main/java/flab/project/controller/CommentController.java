package flab.project.controller;

import flab.project.config.baseresponse.FailResponse;
import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.domain.Comment;
import flab.project.data.enums.PostType;
import flab.project.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private static final long LONG_POLLING_TIMEOUT = 30000L; // 30초

    @Operation(summary = "댓글 작성 API")
    @Parameters({
            @Parameter(name = "postId", description = "게시물의 id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "rootId", description = "최상단 댓글의 id", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "PostType", description = "게시물의 종류", in = ParameterIn.QUERY, required = true)})
    @PostMapping(value = "/posts/{postId}/comment")
    public DeferredResult<SuccessResponse<?>> addComment(@PathVariable("postId") @Positive long postId, @RequestParam("userId") long userId,
                                      @RequestParam("rootId") long rootId, @RequestParam("PostType") PostType postType) {
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .rootId(rootId)
                .build();

        DeferredResult<SuccessResponse<?>> deferredResult = new DeferredResult<>(LONG_POLLING_TIMEOUT);

        deferredResult.onCompletion(() -> log.info("Request processing completed."));

        deferredResult.onTimeout(() -> {
            log.warn("Request processing timeout.");
            deferredResult.setErrorResult(new FailResponse(ResponseEnum.TIME_OUT));
        });

        deferredResult.onError((Throwable t) -> {
            log.error("Request processing error.", t);
            deferredResult.setErrorResult(new FailResponse(ResponseEnum.SERVER_ERROR));
        });

        CompletableFuture.supplyAsync(() -> commentService.addComment(comment, postType))
                .thenAccept(response -> deferredResult.setResult(new SuccessResponse<>(response)))
                .exceptionally(e -> {
                    // Todo 오류의 원인을 e.getCause()로 검사를 해줘야 할까요? 오류의 예시로 뭐가 있을지 잘 모르겠어서 ResponseEnum에 어떤 상수로써 명시해야 할지 잘 모르겠네요.
                    log.error("Error occurred during asynchronous processing", e);
                    deferredResult.setErrorResult(new FailResponse(ResponseEnum.SERVER_ERROR));

                    return null;
                });

        return deferredResult;
    }
}

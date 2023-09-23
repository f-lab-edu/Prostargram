package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.PostWithUser;
import flab.project.data.enums.PostType;
import flab.project.service.PostService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/{postId}/basic-post")
    public SuccessResponse<PostWithUser> getBasicPostDetail(@PathVariable("postId") @Positive long postId)
            throws NotFoundException {
        long userId = 1L;

        return postService.getPostDetail(postId, userId, PostType.BASIC);
    }

    @GetMapping("/posts/{postId}/debate-post")
    public SuccessResponse<PostWithUser> getDebatePostDetail(@PathVariable("postId") @Positive long postId)
            throws NotFoundException {
        long userId = 1L;

        return postService.getPostDetail(postId, userId, PostType.DEBATE);
    }

    @GetMapping("/posts/{postId}/poll-post")
    public SuccessResponse<PostWithUser> getPollPostDetail(@PathVariable("postId") @Positive long postId)
            throws NotFoundException {
        long userId = 1L;

        return postService.getPostDetail(postId, userId, PostType.POLL);
    }
}
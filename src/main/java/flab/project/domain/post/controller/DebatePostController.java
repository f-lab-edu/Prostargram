package flab.project.domain.post.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.post.model.AddDebatePostRequest;
import flab.project.domain.post.model.DebatePost;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.template.PostFacadeTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class DebatePostController {

    private final PostFacadeTemplate debatePostFacade;
    private final FanOutService fanOutService;

    @PostMapping("/posts/debate")
    public SuccessResponse<DebatePost> addDebatePost(
            @LoggedInUserId Long userId,
            @RequestBody @Validated AddDebatePostRequest debatePost
    ) {
        DebatePost createdPost = (DebatePost) debatePostFacade.addPost(userId, debatePost);

        return new SuccessResponse<>(createdPost);
    }
}
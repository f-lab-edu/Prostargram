package flab.project.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.DebatePost;
import flab.project.service.FanOutService;
import flab.project.template.PostFacadeTemplate;
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
        fanOutService.fanOut(userId, createdPost.getPostId());

        return new SuccessResponse<>(createdPost);
    }
}
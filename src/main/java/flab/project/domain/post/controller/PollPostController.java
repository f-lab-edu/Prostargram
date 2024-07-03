package flab.project.domain.post.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.post.model.AddPollPostRequest;
import flab.project.domain.post.model.PollPost;
import flab.project.domain.post.template.PostFacadeTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class PollPostController {

    private final PostFacadeTemplate pollPostFacade;

    @PostMapping("/posts/poll")
    public SuccessResponse<PollPost> addBasicPost(
            @LoggedInUserId Long userId,
            @RequestBody @Validated AddPollPostRequest pollPost
    ) {
        PollPost createdPost = (PollPost) pollPostFacade.addPost(userId, pollPost);

        return new SuccessResponse<>(createdPost);
    }
}
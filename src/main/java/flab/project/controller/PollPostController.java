package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddPollPostRequest;
import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.PollPost;
import flab.project.template.PostFacadeTemplate;
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
            @RequestBody @Validated AddPollPostRequest pollPost
    ) {
        long userId = 1L;

        PollPost createdPost = (PollPost) pollPostFacade.addPost(userId, pollPost);

        return new SuccessResponse<>(createdPost);
    }
}
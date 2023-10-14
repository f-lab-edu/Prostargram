package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.data.dto.model.BasePost;
import flab.project.data.dto.model.DebatePost;
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

    @PostMapping("/posts/debate")
    public SuccessResponse<DebatePost> addDebatePost(
            @RequestBody @Validated AddDebatePostRequest debatePost
    ) {
        long userId = 1L;

        DebatePost createdPost = (DebatePost) debatePostFacade.addPost(userId, debatePost);

        return new SuccessResponse<>(createdPost);
    }
}
package flab.project.facade;

import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.data.dto.model.AddPostRequest;
import flab.project.service.PostHashTagService;
import flab.project.service.PostOptionService;
import flab.project.service.PostService;
import flab.project.template.PostFacadeTemplate;
import org.springframework.stereotype.Service;

@Service
public class DebatePostFacade extends PostFacadeTemplate {

    private final PostOptionService postOptionService;

    public DebatePostFacade(
            PostService postService,
            PostHashTagService postHashTagService,
            PostOptionService postOptionService
    ) {
        super(postService, postHashTagService);
        this.postOptionService = postOptionService;
    }

    @Override
    protected void specializedMethod(long userId, AddPostRequest post) {
        if (!(post instanceof AddDebatePostRequest debatePost)) {
            throw new RuntimeException();
        }

        postOptionService.savePostOptions(debatePost.getPostId(), debatePost.getOptionContents());
    }
}
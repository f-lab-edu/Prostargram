package flab.project.facade;

import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.data.dto.model.AddPostRequest;
import flab.project.service.PostHashTagService;
import flab.project.service.DebatePostOptionService;
import flab.project.service.PostService;
import flab.project.template.PostFacadeTemplate;
import flab.project.template.PostOptionServiceTemplate;
import org.springframework.stereotype.Service;

@Service
public class DebatePostFacade extends PostFacadeTemplate {

    private final PostOptionServiceTemplate postOptionServiceTemplate;

    public DebatePostFacade(
            PostService postService,
            PostHashTagService postHashTagService,
            DebatePostOptionService debatePostOptionService
    ) {
        super(postService, postHashTagService);
        this.postOptionServiceTemplate = debatePostOptionService;
    }

    @Override
    protected void specializedMethod(long userId, AddPostRequest post) {
        if (!(post instanceof AddDebatePostRequest debatePost)){
            throw new RuntimeException();
        }

        postOptionServiceTemplate.savePostOptions(debatePost.getPostId(),debatePost.getOptionContents());
    }
}

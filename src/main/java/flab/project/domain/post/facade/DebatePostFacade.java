package flab.project.domain.post.facade;

import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.model.AddDebatePostRequest;
import flab.project.domain.post.model.AddPostRequest;
import flab.project.domain.post.model.BasePost;
import flab.project.domain.post.model.DebatePost;
import flab.project.domain.post.service.PostHashTagService;
import flab.project.domain.post.service.PostService;
import flab.project.domain.post.template.PostFacadeTemplate;
import flab.project.domain.post.template.PostOptionServiceTemplate;
import flab.project.domain.user.model.Option;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DebatePostFacade extends PostFacadeTemplate {

    private final PostOptionServiceTemplate postOptionServiceTemplate;

    public DebatePostFacade(
        PostService postService,
        PostHashTagService postHashTagService,
        FanOutService fanOutService,
        PostOptionServiceTemplate debatePostOptionService
    ) {
        super(postService, postHashTagService, fanOutService);
        this.postOptionServiceTemplate = debatePostOptionService;
    }

    @Override
    protected void validateTypeOfPost(AddPostRequest post) {
        if (!(post instanceof AddDebatePostRequest)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected BasePost handlePostMetadata(long userId, AddPostRequest post) {
        AddDebatePostRequest debatePostRequest = (AddDebatePostRequest) post;
        Set<Option> options = postOptionServiceTemplate.savePostOptions(debatePostRequest.getPostId(), debatePostRequest.getOptionContents());

        return new DebatePost(debatePostRequest, userId, options);
    }

    @Override
    protected boolean doesNeedFanOut() {
        return true;
    }
}
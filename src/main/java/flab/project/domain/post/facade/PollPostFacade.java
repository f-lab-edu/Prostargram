package flab.project.domain.post.facade;

import flab.project.domain.post.model.AddPollPostRequest;
import flab.project.domain.post.model.AddPostRequest;
import flab.project.domain.user.model.Option;
import flab.project.domain.post.model.PollPost;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.service.PollMetadataService;
import flab.project.domain.post.service.PostHashTagService;
import flab.project.domain.post.service.PostService;
import flab.project.domain.post.template.PostFacadeTemplate;
import flab.project.domain.post.template.PostOptionServiceTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PollPostFacade extends PostFacadeTemplate {

    private final PollMetadataService pollMetadataService;
    private final PostOptionServiceTemplate postOptionServiceTemplate;

    public PollPostFacade(
        PostService postService,
        PostHashTagService postHashTagService,
        FanOutService fanOutService,
        PollMetadataService pollMetadataService,
        PostOptionServiceTemplate pollPostOptionService
    ) {
        super(postService, postHashTagService, fanOutService);
        this.pollMetadataService = pollMetadataService;
        this.postOptionServiceTemplate = pollPostOptionService;
    }

    @Override
    protected void validateTypeOfPost(AddPostRequest post) {
        if (!(post instanceof AddPollPostRequest)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected PollPost handlePostMetadata(long userId, AddPostRequest post) {
        AddPollPostRequest pollPostRequest = (AddPollPostRequest) post;

        pollMetadataService.addMetadata(pollPostRequest);
        Set<Option> options = postOptionServiceTemplate.savePostOptions(pollPostRequest.getPostId(), pollPostRequest.getOptionContents());

        return new PollPost(pollPostRequest, userId, options);
    }

    @Override
    protected boolean doesNeedFanOut() {
        return false;
    }
}
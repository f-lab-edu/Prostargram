package flab.project.facade;

import flab.project.data.dto.model.AddPollPostRequest;
import flab.project.data.dto.model.AddPostRequest;
import flab.project.service.*;
import flab.project.template.PostFacadeTemplate;
import flab.project.template.PostOptionServiceTemplate;
import org.springframework.stereotype.Service;

@Service
public class PollPostFacade extends PostFacadeTemplate {

    private final PollMetadataService pollMetadataService;
    private final PostOptionServiceTemplate postOptionServiceTemplate;

    public PollPostFacade(
            PostService postService,
            PostHashTagService postHashTagService,
            PollMetadataService pollMetadataService,
            PollPostOptionService pollPostOptionService
    ) {
        super(postService, postHashTagService);
        this.pollMetadataService = pollMetadataService;
        this.postOptionServiceTemplate = pollPostOptionService;
    }

    @Override
    protected void specializedMethod(long userId, AddPostRequest post) {
        if (!(post instanceof AddPollPostRequest pollPost)) {
            throw new RuntimeException();
        }

        pollMetadataService.addMetadata(pollPost);
        postOptionServiceTemplate.savePostOptions(pollPost.getPostId(), pollPost.getOptionContents());
    }
}
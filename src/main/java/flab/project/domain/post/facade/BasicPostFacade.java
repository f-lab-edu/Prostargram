package flab.project.domain.post.facade;

import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.post.model.AddPostRequest;
import flab.project.domain.post.model.BasePost;
import flab.project.domain.post.model.BasicPost;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.service.PostHashTagService;
import flab.project.domain.post.service.PostImageService;
import flab.project.domain.post.service.PostService;
import flab.project.domain.post.template.PostFacadeTemplate;

import org.springframework.stereotype.Service;

@Service
public class BasicPostFacade extends PostFacadeTemplate {

    private final PostImageService postImageService;

    public BasicPostFacade(
        PostService postService,
        PostHashTagService postHashTagService,
        FanOutService fanOutService,
        PostImageService postImageService
    ) {
        super(postService, postHashTagService, fanOutService);
        this.postImageService = postImageService;
    }

    @Override
    protected void validateTypeOfPost(AddPostRequest post) {
        if (!(post instanceof AddBasicPostRequest)) {
            throw new RuntimeException();
        }
    }

    @Override
    protected BasePost handlePostMetadata(long userId, AddPostRequest post) {
        AddBasicPostRequest addBasicPostRequest = (AddBasicPostRequest) post;

        postImageService.saveAll(addBasicPostRequest.getPostId(), addBasicPostRequest.getContentUrls());

        return new BasicPost(addBasicPostRequest, userId);
    }

    @Override
    protected boolean doesNeedFanOut() {
        return true;
    }
}
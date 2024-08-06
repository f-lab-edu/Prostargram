package flab.project.domain.post.facade;

import flab.project.common.FileStorage.FileStorage;
import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.post.model.AddPostRequest;
import flab.project.domain.post.model.BasePost;
import flab.project.domain.post.model.BasicPost;
import flab.project.domain.file.enums.FileType;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.service.PostHashTagService;
import flab.project.domain.post.service.PostImageService;
import flab.project.domain.post.service.PostService;
import flab.project.domain.post.template.PostFacadeTemplate;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class BasicPostFacade extends PostFacadeTemplate {

    private final FileStorage fileStorage;
    private final PostImageService postImageService;

    public BasicPostFacade(
        PostService postService,
        PostHashTagService postHashTagService,
        FanOutService fanOutService,
        FileStorage fileStorage,
        PostImageService postImageService
    ) {
        super(postService, postHashTagService, fanOutService);
        this.fileStorage = fileStorage;
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
        AddBasicPostRequest basicPostRequest = (AddBasicPostRequest) post;

        Set<String> uploadedFileUrls
            = fileStorage.uploadFiles(userId, basicPostRequest.getContentImages(), FileType.POST_IMAGE);
        postImageService.saveAll(basicPostRequest.getPostId(), uploadedFileUrls);

        return new BasicPost(basicPostRequest, userId, uploadedFileUrls);
    }

    @Override
    protected boolean doesNeedFanOut() {
        return true;
    }
}
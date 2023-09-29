package flab.project.facade;

import flab.project.common.FileStorage.FileStorage;
import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.data.dto.model.AddPostRequest;
import flab.project.data.enums.FileType;
import flab.project.service.PostHashTagService;
import flab.project.service.PostImageService;
import flab.project.service.PostService;
import flab.project.template.PostFacadeTemplate;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class BasicPostFacade extends PostFacadeTemplate {

    private final FileStorage fileStorage;
    private final PostImageService postImageService;

    public BasicPostFacade(
            PostService postService,
            PostHashTagService postHashTagService,
            FileStorage fileStorage,
            PostImageService postImageService
    ) {
        super(postService, postHashTagService);
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
    protected void handlePostMetadata(long userId, AddPostRequest post) {
        AddBasicPostRequest basicPostRequest = (AddBasicPostRequest) post;

        Set<String> uploadedFileUrls = fileStorage.uploadFiles(userId, basicPostRequest.getContentImages(), FileType.POST_IMAGE);
        postImageService.saveAll(basicPostRequest.getPostId(), uploadedFileUrls);
    }
}
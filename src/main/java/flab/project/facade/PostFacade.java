package flab.project.facade;

import flab.project.common.FileStorage.FileStorage;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.data.enums.FileType;
import flab.project.service.PostImageService;
import flab.project.service.PostHashTagService;
import flab.project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostFacade {

    private final PostService postService;
    private final PostHashTagService postHashTagService;
    private final FileStorage fileStorage;
    private final PostImageService postImageService;

    @Transactional
    public SuccessResponse addBasicPost(long userId, AddBasicPostRequest basicPost, List<MultipartFile> contentImages) {
        postService.addBasicPost(userId, basicPost);

        postHashTagService.saveAll(basicPost.getPostId(), basicPost.getHashTagNames());

        List<String> uploadedFileUrls = fileStorage.uploadFiles(userId, contentImages, FileType.POST_IMAGE);
        postImageService.saveAll(basicPost.getPostId(), uploadedFileUrls);

        return new SuccessResponse();
    }
}
package flab.project.facade;

import flab.project.common.FileStorage.FileStorage;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.data.dto.model.AddDebatePostRequest;
import flab.project.data.dto.model.AddPollPostRequest;
import flab.project.data.enums.FileType;
import flab.project.mapper.HashTagMapper;
import flab.project.service.*;
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
    private final PostOptionService postOptionService;
    private final PollMetadataService pollMetadataService;

    @Transactional
    public SuccessResponse addBasicPost(long userId, AddBasicPostRequest basicPost, List<MultipartFile> contentImages) {
        postService.addPost(userId, basicPost);

        postHashTagService.saveAll(basicPost.getPostId(), basicPost.getHashTagNames());

        List<String> uploadedFileUrls = fileStorage.uploadFiles(userId, contentImages, FileType.POST_IMAGE);
        postImageService.saveAll(basicPost.getPostId(), uploadedFileUrls);

        return new SuccessResponse();
    }

    @Transactional
    public SuccessResponse addDebatePost(long userId, AddDebatePostRequest debatePost) {
        postService.addPost(userId, debatePost);

        postHashTagService.saveAll(debatePost.getPostId(), debatePost.getHashTagNames());

        postOptionService.saveDebatePostOptions(debatePost.getPostId(),debatePost.getOptionContents());

        return new SuccessResponse<>();
    }

    @Transactional
    public SuccessResponse addPollPost(long userId, AddPollPostRequest pollPost) {
        postService.addPost(userId, pollPost);

        postHashTagService.saveAll(pollPost.getPostId(), pollPost.getHashTagNames());

        pollMetadataService.addMetadata(pollPost);
        postOptionService.savePollPostOptions(pollPost.getPostId(),pollPost.getOptionContents());

        return new SuccessResponse();
    }
}
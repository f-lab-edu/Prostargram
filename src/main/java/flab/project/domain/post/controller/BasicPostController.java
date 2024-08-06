package flab.project.domain.post.controller;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.post.model.AddBasicPostRequest;
import flab.project.domain.post.model.BasicPost;
import flab.project.domain.feed.service.FanOutService;
import flab.project.domain.post.template.PostFacadeTemplate;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RequiredArgsConstructor
@RestController
public class BasicPostController {

    private final PostFacadeTemplate basicPostFacade;
    private final FanOutService fanOutService;

    @PostMapping("/posts/basic")
    public SuccessResponse<BasicPost> addBasicPost(
            @LoggedInUserId Long userId,
            @RequestPart("basicPost") @Validated AddBasicPostRequest basicPost,
            @RequestPart("contentImages") List<MultipartFile> contentImages
    ) {
        basicPost.setContentImages(contentImages);

        BasicPost createdPost = (BasicPost) basicPostFacade.addPost(userId, basicPost);

        return new SuccessResponse<>(createdPost);
    }
}
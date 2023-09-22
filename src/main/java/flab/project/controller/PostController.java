package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.BasicPost;
import flab.project.facade.PostFacade;
import flab.project.service.PostService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostFacade postFacade;

    @PostMapping("posts/basic-post")
    public SuccessResponse addBasicPost(
            @Validated @RequestBody BasicPost basicPost,
            @RequestPart(value = "contentImages") @Nullable List<MultipartFile> contentImages
    ) {
        long userId = 1L;

        return postFacade.addBasicPost(userId, basicPost, contentImages);
    }
}

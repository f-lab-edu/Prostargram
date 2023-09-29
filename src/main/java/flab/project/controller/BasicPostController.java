package flab.project.controller;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.model.AddBasicPostRequest;
import flab.project.template.PostFacadeTemplate;

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

    @PostMapping("/posts/basic")
    public SuccessResponse<Void> addBasicPost(
            @RequestPart("basicPost") @Validated AddBasicPostRequest basicPost,
            @RequestPart("contentImages") List<MultipartFile> contentImages
    ) {
        long userId = 1L;

        basicPost.setContentImages(contentImages);

        basicPostFacade.addPost(userId, basicPost);

        return new SuccessResponse<>();
    }
}
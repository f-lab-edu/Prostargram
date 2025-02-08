package flab.project.domain.file;

import flab.project.common.annotation.LoggedInUserId;
import flab.project.common.file_storage.FileUploader;
import flab.project.common.file_storage.UploadedFileUrls;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.domain.file.model.ImageUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final FileUploader fileUploader;

    @PostMapping("/images")
    public SuccessResponse<UploadedFileUrls> requestPresignedUrls(
            @LoggedInUserId Long userId,
            @RequestBody ImageUploadRequest imageUploadRequest
    ) {
        UploadedFileUrls uploadedFileUrls = fileUploader.generatePreSignedUrls(userId, imageUploadRequest);

        return new SuccessResponse<>(uploadedFileUrls);
    }
}

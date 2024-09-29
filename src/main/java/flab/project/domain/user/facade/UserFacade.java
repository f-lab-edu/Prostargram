package flab.project.domain.user.facade;

import flab.project.common.FileStorage.FileStorage;
import flab.project.common.FileStorage.UploadedFileUrl;
import flab.project.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static flab.project.domain.file.enums.FileType.PROFILE_IMAGE;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final FileStorage fileStorage;
    private final UserService userService;

    public UploadedFileUrl updateProfileImage(long userId) {
        UploadedFileUrl uploadedFileUrl = fileStorage.generatePreSignedUrl(userId, PROFILE_IMAGE);

        userService.updateProfileImage(userId, uploadedFileUrl.getContentUrl());

        return uploadedFileUrl;
    }
}
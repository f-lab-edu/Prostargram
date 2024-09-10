package flab.project.domain.user.facade;

import flab.project.common.FileStorage.BucketUtils;
import flab.project.common.FileStorage.FileExtensionFilter;
import flab.project.common.FileStorage.FileStorage;
import flab.project.config.exception.FailedToUpdateProfileImageToDatabaseException;
import flab.project.config.exception.NotImageExtensionOrNotSupportedExtensionException;
import flab.project.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static flab.project.domain.file.enums.FileType.PROFILE_IMAGE;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final FileStorage fileStorage;
    private final UserService userService;
    private final FileExtensionFilter fileExtensionFilter;

    public void updateProfileImage(long userId, MultipartFile profileImg) {
        try {
            fileExtensionFilter.filterImageFileExtension(profileImg);

            List<String> fileNamesInBucket = fileStorage.getFileNamesInBucket(userId);
            String uploadedProfileImgUrl
                    = fileStorage.uploadFile(userId, profileImg, PROFILE_IMAGE);

            boolean isSuccess = userService.updateProfileImage(userId, uploadedProfileImgUrl);

            if (!isSuccess) {
                fileStorage.deleteFile(BucketUtils.getBaseBucketName(PROFILE_IMAGE), uploadedProfileImgUrl);

                throw new FailedToUpdateProfileImageToDatabaseException();
            }

            if (!fileNamesInBucket.isEmpty()) {
                String existingProfileImgFileName = fileNamesInBucket.get(0);

                fileStorage.deleteFile(BucketUtils.getBaseBucketName(PROFILE_IMAGE), existingProfileImgFileName);
            }
        } catch (NotImageExtensionOrNotSupportedExtensionException notImageExtensionOrNotSupportedExtensionException) {
            throw new NotImageExtensionOrNotSupportedExtensionException();
        } catch (Exception e) {
            throw new FailedToUpdateProfileImageToDatabaseException();
        }
    }
}
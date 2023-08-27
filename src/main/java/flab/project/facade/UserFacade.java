package flab.project.facade;

import flab.project.common.FileStorage.FileStorage;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.FailedToReflectProfileImageToDatabaseException;
import flab.project.data.dto.file.ProfileImage;
import flab.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static flab.project.data.enums.FileType.PROFILE_IMAGE;

@RequiredArgsConstructor
@Service
public class UserFacade {

    private final FileStorage fileStorage;
    private final UserService userService;

    public SuccessResponse updateProfileImage(long userId, MultipartFile profileImg) {
        try {
            //todo 확장자 필터링하기.

            List<String> fileNamesInBucket = fileStorage.getFileNamesInBucket(userId);
            String uploadedProfileImgUrl = fileStorage.uploadFile(userId, profileImg, PROFILE_IMAGE);

            boolean isSuccess = userService.updateProfileImage(userId, uploadedProfileImgUrl);

            if (!isSuccess) {
                fileStorage.deleteFile(ProfileImage.BASE_BUCKET_NAME, uploadedProfileImgUrl);
                throw new FailedToReflectProfileImageToDatabaseException();
            }

            if (fileNamesInBucket.size() > 0) {
                String existingProfileImgFileName = fileNamesInBucket.get(0);

                fileStorage.deleteFile(ProfileImage.BASE_BUCKET_NAME, existingProfileImgFileName);
            }

            return new SuccessResponse();
        } catch (Exception e) {
            throw new FailedToReflectProfileImageToDatabaseException();
        }
    }
}

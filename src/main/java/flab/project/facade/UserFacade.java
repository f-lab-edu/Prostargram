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
        // 기존 프로필 이미지 가져오기
        try {
            //todo 확장자 필터링하기. 무슨 무슨 확장자만 허용할까..?

            List<String> fileNamesInBucket = fileStorage.getFileNamesInBucket(userId);
            String uploadedProfileImgUrl = fileStorage.uploadFile(userId, profileImg, PROFILE_IMAGE);

            boolean isSuccess = userService.updateProfileImage(userId, uploadedProfileImgUrl);

            if (!isSuccess) {
                fileStorage.deleteFile(ProfileImage.BASE_BUCKET_NAME, uploadedProfileImgUrl);
                throw new FailedToReflectProfileImageToDatabaseException();
            }

            if (fileNamesInBucket.size() > 0) {
                // todo 프로필 이미지는 정상적인 처리 로직을 거쳤다면, 1개만 있어야 하는게 맞긴 한데.. 이렇게 하는게 옳을까?
                String existingProfileImgFileName = fileNamesInBucket.get(0);

                //todo 범용성 있도록 수정
                fileStorage.deleteFile(ProfileImage.BASE_BUCKET_NAME, existingProfileImgFileName);
            }


            return new SuccessResponse();
        } catch (Exception e) {

            throw new FailedToReflectProfileImageToDatabaseException();
        }


    }
}

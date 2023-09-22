package flab.project.facade;

import flab.project.common.FileStorage.BaseBucketName;
import flab.project.common.FileStorage.FileExtensionFilter;
import flab.project.common.FileStorage.FileStorage;
import flab.project.config.exception.FailedToUpdateProfileImageToDatabaseException;
import flab.project.data.enums.FileType;
import flab.project.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static flab.project.data.enums.FileType.PROFILE_IMAGE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @InjectMocks
    private UserFacade userFacade;
    @Mock
    private FileStorage fileStorage;
    @Mock
    private UserService userService;
    @Mock
    private FileExtensionFilter fileExtensionFilter;

    @DisplayName("프로필 이미지를 수정할 수 있다.")
    @Test
    void updateProfileImage() {
        // given
        long userId = 1L;
        String uploadedProfileImageUrl = "https://uploadedProfileImage.com";

        MultipartFile multipartFile = new MockMultipartFile(
                "profileImage",
                "test.txt",
                "text/plain",
                "test file".getBytes()
        );

        given(fileStorage.getFileNamesInBucket(anyLong()))
                .willReturn(List.of("test-file"));
        given(fileStorage.uploadFile(anyLong(), any(MultipartFile.class), any(FileType.class)))
                .willReturn(uploadedProfileImageUrl);
        given(userService.updateProfileImage(anyLong(), anyString()))
                .willReturn(true);

        // when
        userFacade.updateProfileImage(userId, multipartFile);

        // then
        then(fileStorage).should().getFileNamesInBucket(userId);
        then(fileStorage).should().uploadFile(userId, multipartFile, PROFILE_IMAGE);
        then(userService).should().updateProfileImage(userId, uploadedProfileImageUrl);
        then(fileStorage).should().deleteFile(BaseBucketName.getBaseBucektName(PROFILE_IMAGE), "test-file");
    }

    @DisplayName("DB에 새로 등록된 프로필 이미지 반영을 실패했을 경우, 업로드된 파일이 삭제되어야 한다.")
    @Test
    void uploadedFileMustBeDeletedWhenFailedToReflectProfileImageToDatabase() {
        // given
        long userId = 1L;
        String uploadedProfileImageUrl = "https://uploadedProfileImage.com";

        MultipartFile multipartFile = new MockMultipartFile(
                "profileImage",
                "test.jpg",
                "text/plain",
                "test file".getBytes()
        );

        given(fileStorage.uploadFile(anyLong(), any(MultipartFile.class), any(FileType.class)))
                .willReturn(uploadedProfileImageUrl);
        given(userService.updateProfileImage(anyLong(), anyString()))
                .willReturn(false);

        // when & then
        assertThatThrownBy(() -> userFacade.updateProfileImage(userId, multipartFile))
                .isInstanceOf(FailedToUpdateProfileImageToDatabaseException.class);

        then(fileStorage).should().deleteFile(BaseBucketName.getBaseBucektName(PROFILE_IMAGE), uploadedProfileImageUrl);
    }

    @DisplayName("기존 파일이 없을 경우, 기존 파일 삭제 메서드는 호출되지 않는다.")
    @Test
    void deleteExistingFileMethodNotCalledWhenExistingFileDoesNotExist() {
        // given
        long userId = 1L;
        String uploadedProfileImageUrl = "https://uploadedProfileImage.com";

        MultipartFile multipartFile = new MockMultipartFile(
                "profileImage",
                "test.txt",
                "text/plain",
                "test file".getBytes()
        );

        given(fileStorage.getFileNamesInBucket(anyLong()))
                .willReturn(new ArrayList());
        given(fileStorage.uploadFile(anyLong(), any(MultipartFile.class), any(FileType.class)))
                .willReturn(uploadedProfileImageUrl);
        given(userService.updateProfileImage(anyLong(), anyString()))
                .willReturn(true);

        // when
        userFacade.updateProfileImage(userId, multipartFile);

        // then
        then(fileStorage).should(never()).deleteFile(eq(BaseBucketName.getBaseBucektName(PROFILE_IMAGE)), anyString());
    }
}
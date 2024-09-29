package flab.project.facade;

import flab.project.common.fileStorage.FileStorage;
import flab.project.common.fileStorage.UploadedFileUrl;
import flab.project.domain.file.enums.FileType;
import flab.project.domain.user.facade.UserFacade;
import flab.project.domain.user.service.UserService;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static flab.project.domain.file.enums.FileType.PROFILE_IMAGE;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @InjectMocks
    private UserFacade userFacade;
    @Mock
    private FileStorage fileStorage;
    @Mock
    private UserService userService;

    @DisplayName("프로필 이미지를 수정할 수 있다.")
    @Test
    void updateProfileImage() throws MalformedURLException {
        // given
        long userId = 1L;
        URL url = new URL("https", "kr.ncp.com", "/file/path");
        UploadedFileUrl uploadedFileUrl = new UploadedFileUrl(url);

        given(fileStorage.generatePreSignedUrl(anyLong(), any(FileType.class)))
                .willReturn(uploadedFileUrl);
        given(userService.updateProfileImage(anyLong(), anyString()))
                .willReturn(true);

        // when
        userFacade.updateProfileImage(userId);

        // then
        then(fileStorage).should().generatePreSignedUrl(userId, PROFILE_IMAGE);
        then(userService).should().updateProfileImage(userId, "https://kr.ncp.com/file/path");
    }
}
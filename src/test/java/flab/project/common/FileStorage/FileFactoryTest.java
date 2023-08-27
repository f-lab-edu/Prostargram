package flab.project.common.FileStorage;

import flab.project.data.dto.file.ProfileImage;
import flab.project.data.dto.file.Uploadable;
import flab.project.data.enums.FileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileFactoryTest {

    @DisplayName("FileType으로 PROFILE_IMAGE가 넘어오면 ProfileImage 객체를 생성하여 반환한다.")
    @Test
    void returnProfileImageWhenParameterFileTypeIsPROFILE_IMAGE() {
        // given
        MultipartFile multipartFile = new MockMultipartFile(
                "profileImage",
                "test.txt",
                "text/plain",
                "test file".getBytes()
        );
        FileFactory fileFactory = new FileFactory();

        // when
        Uploadable fileInfo = fileFactory.getFileInfo(1L, multipartFile, FileType.PROFILE_IMAGE);

        // then
        assertThat(fileInfo).isInstanceOf(ProfileImage.class);
    }
}
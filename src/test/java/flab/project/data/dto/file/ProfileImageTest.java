package flab.project.data.dto.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProfileImageTest {

    @DisplayName("ProfileImage 생성자는 bucketName,fileName,objectMetadata를 초기화 하는 로직을 가지고 있다.")
    @Test
    void profileImageConstructorInitializeFields() {
        MultipartFile multipartFile = new MockMultipartFile("profileImage", "test.txt",
                "text/plain", "test file".getBytes());

        ProfileImage profileImage = new ProfileImage(1L, multipartFile);

        assertThat(profileImage.getBucketName()).isEqualTo("profileimage/1");
        String fileName = profileImage.getFileName();
        assertThat(fileName.substring(fileName.lastIndexOf("."))).isEqualTo(".txt");
    }
}
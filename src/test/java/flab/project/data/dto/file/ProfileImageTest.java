package flab.project.data.dto.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.StringBufferInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProfileImageTest {

    @Mock
    private MultipartFile multipartFile;

    @DisplayName("프로필 이미지 생성자 테스트")
    @Test
    void checkProfileImageConstructor() throws IOException {
        // given
        String testContent = "test content";

        given(multipartFile.getOriginalFilename()).willReturn("test.jpg");
        given(multipartFile.getInputStream()).willReturn(new StringBufferInputStream(testContent));

        // when
        ProfileImage profileImage = new ProfileImage(1L, multipartFile);

        String bucketName = profileImage.getBucketName();
        String fileName = profileImage.getFileName();
        long contentLength = profileImage.getObjectMetadata().getContentLength();

        // then
        then(multipartFile).should().getInputStream();

        assertThat(bucketName).isEqualTo("profileimage/1");
        assertThat(fileName.substring(fileName.lastIndexOf("."))).isEqualTo(".jpg");
        assertThat(contentLength).isEqualTo(testContent.length());
    }
}
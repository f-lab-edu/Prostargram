package flab.project.common.FileStorage;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class FileExtensionExtractorTest {

    @DisplayName("MultipartFile에서 확장자를 추출할 수 있다.")
    @Test
    public void extractFileExtension() {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "test",
            "test.jpg",
            null,
            "test".getBytes()
        );

        // when
        String extension = FileExtensionExtractor.extractFileExtension(mockMultipartFile);

        // then
        assertThat(extension).isEqualTo("jpg");
    }
}
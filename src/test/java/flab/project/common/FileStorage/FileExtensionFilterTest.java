package flab.project.common.FileStorage;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import flab.project.config.exception.NotImageExtensionOrNotSupportedExtensionException;
import flab.project.data.enums.ExtensionType;
import flab.project.mapper.FileExtensionMapper;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class FileExtensionFilterTest {

    private FileExtensionFilter fileExtensionFilter;

    @Mock
    private FileExtensionMapper fileExtensionMapper;

    @DisplayName("이미지 확장자인지를 검사할 수 있다.")
    @Test
    public void filterImageFileExtension() {
        // given
        given(fileExtensionMapper.findAllByType(any(ExtensionType.class)))
            .willReturn(new HashSet<>(List.of("jpg", "jpeg", "png")));

        MockMultipartFile mockMultipartFile
            = new MockMultipartFile(
            "test-image.jpg",
            "test-image.jpg",
            null,
            "test-content".getBytes()
        );

        this.fileExtensionFilter = new FileExtensionFilter(fileExtensionMapper);

        // when & then
        assertThatCode(() ->
            fileExtensionFilter.filterImageFileExtension(mockMultipartFile)
        ).doesNotThrowAnyException();
    }

    @DisplayName("이미지 확장자가 아니면, NotImageExtensionOrNotSupportedExtensionException을 던진다.")
    @Test
    public void throwNotImageExtensionOrNotSupportedExtensionExceptionIfNotImageFileExtension() {
        // given
        given(fileExtensionMapper.findAllByType(any(ExtensionType.class)))
            .willReturn(new HashSet<>(List.of("jpg", "jpeg", "png")));

        MockMultipartFile mockMultipartFile
            = new MockMultipartFile(
            "test-image.jpg",
            "test-image.not-supported-extension",
            null,
            "test-content".getBytes()
        );

        this.fileExtensionFilter = new FileExtensionFilter(fileExtensionMapper);

        // when & then
        assertThatThrownBy(() -> fileExtensionFilter.filterImageFileExtension(mockMultipartFile))
            .isInstanceOf(NotImageExtensionOrNotSupportedExtensionException.class);
    }
}
package flab.project.common.FileStorage;

import static flab.project.domain.file.enums.ExtensionType.IMAGE;

import flab.project.config.exception.NotImageExtensionOrNotSupportedExtensionException;
import flab.project.domain.file.mapper.FileExtensionMapper;
import java.util.HashSet;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileExtensionFilter {

    private HashSet<String> imageFileExtensions;
    private final FileExtensionMapper fileExtensionMapper;

    // TODO 추후, imageFileExtension은 Lazy Initilization으로 수정해도 좋을 것 같음.
    public FileExtensionFilter(
            FileExtensionMapper fileExtensionMapper
    ) {
        this.fileExtensionMapper = fileExtensionMapper;
        this.imageFileExtensions = fileExtensionMapper.findAllByType(IMAGE);
    }

    public void filterImageFileExtension(MultipartFile file) {
        String extension = FileExtensionExtractor.extractFileExtension(file);

        boolean isContain = imageFileExtensions.contains(extension);

        if (!isContain) {
            throw new NotImageExtensionOrNotSupportedExtensionException();
        }
    }
}

package flab.project.common.FileStorage;

import org.springframework.web.multipart.MultipartFile;

public class FileExtensionExtractor {

    public static String extractFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
}

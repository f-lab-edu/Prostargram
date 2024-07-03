package flab.project.domain.file.model;

import com.amazonaws.services.s3.model.ObjectMetadata;
import flab.project.common.FileStorage.BucketUtils;
import flab.project.common.FileStorage.FileExtensionExtractor;
import flab.project.domain.file.enums.FileType;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Getter
public class File implements Uploadable {

    private final String bucketName;
    private final String fileName;
    private final ObjectMetadata objectMetadata;

    public File(long suffix, MultipartFile file, FileType fileType) {
        this.bucketName = BucketUtils.getBaseBucketName(fileType) + "/" + suffix;
        this.fileName = createFileName(file);
        this.objectMetadata = getObjectMetadata(file);
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getInputStream().available());

            return objectMetadata;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createFileName(MultipartFile file) {
        String extension = FileExtensionExtractor.extractFileExtension(file);

        return UUID.randomUUID() + "." + extension;
    }
}
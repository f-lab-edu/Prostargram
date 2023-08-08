package flab.project.data.file;

import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.UUID;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProfileImgage implements UploadingFile{
    private static final String BASE_BUCKET_NAME="profileimage/";
    private final String bucketName;
    private final String fileName;
    ObjectMetadata objectMetadata;

    public ProfileImgage(long userId, MultipartFile file) {
        this.bucketName = BASE_BUCKET_NAME + userId;
        this.fileName = createFileName(file);
        this.objectMetadata = getObjectMetadata(file);;
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
        String extension = getFileExtension(file);
        return UUID.randomUUID() + extension;
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}

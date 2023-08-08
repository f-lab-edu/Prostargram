package flab.project;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import flab.project.data.file.ProfileImgage;
import flab.project.data.enums.FileType;
import flab.project.data.file.UploadingFile;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ObjectStorage implements FileUploader {

    private final AmazonS3 amazonS3;
    private final FileFactory fileFactory;

    public String uploadFile(long userId, List<MultipartFile> multipartFiles, FileType fileType) {

        try {
            UploadingFile fileInfo = fileFactory.getFileInfo(userId, multipartFiles, fileType);
            String bucketName = fileInfo.getBucketName();
            String fileName = fileInfo.getFileName();
            ObjectMetadata objectMetadata = fileInfo.getObjectMetadata();

            for (MultipartFile multipartFile : multipartFiles) {
                amazonS3.putObject(
                    bucketName,
                    fileName,
                    multipartFile.getInputStream(),
                    objectMetadata
                );
            }

            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (SdkClientException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void getFiles() {
    }

    public void deleteFiles() {
    }
}

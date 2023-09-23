package flab.project.common.FileStorage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import flab.project.data.dto.file.ProfileImage;
import flab.project.data.dto.file.Uploadable;
import flab.project.data.enums.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class FileStorage {

    private final AmazonS3 amazonS3;
    private final FileFactory fileFactory;

    public String uploadFile(long userId, MultipartFile multipartFile, FileType fileType) {
        try {
            Uploadable fileInfo = fileFactory.getFileInfo(userId, multipartFile, fileType);
            String bucketPath = fileInfo.getBucketName();
            String fileName = fileInfo.getFileName();
            ObjectMetadata objectMetadata = fileInfo.getObjectMetadata();

            amazonS3.putObject(
                    bucketPath,
                    fileName,
                    multipartFile.getInputStream(),
                    objectMetadata
            );

            return amazonS3.getUrl(bucketPath, fileName).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getFileNamesInBucket(long userId) {
        String folder = "" + userId;
        String targetBucketName = ProfileImage.BASE_BUCKET_NAME;

        ListObjectsRequest listObjectsRequest
                = new ListObjectsRequest()
                .withBucketName(targetBucketName)
                .withPrefix(folder + "/")
                .withDelimiter("/");

        ObjectListing objectListing = amazonS3.listObjects(listObjectsRequest);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();

        List<String> fileNames = objectSummaries.stream()
                .map(S3ObjectSummary::getKey)
                .filter(fileName -> fileName.trim().length() != 0)
                .collect(Collectors.toList());

        return fileNames;
    }

    public void deleteFile(String bucketName, String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
    }
}
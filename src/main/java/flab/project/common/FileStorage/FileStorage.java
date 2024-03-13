package flab.project.common.FileStorage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import flab.project.data.dto.file.File;
import flab.project.data.dto.file.Uploadable;
import flab.project.data.enums.FileType;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static flab.project.data.enums.FileType.PROFILE_IMAGE;

@RequiredArgsConstructor
@Component
public class FileStorage {

    private final AmazonS3 amazonS3;

    public Set<String> uploadFiles(long userId, List<MultipartFile> multipartFiles, FileType fileType) {
        return multipartFiles.stream()
                .map(multipartFile -> uploadFile(userId, multipartFile, fileType))
                .collect(Collectors.toSet());
    }

    public String uploadFile(long userId, MultipartFile multipartFile, FileType fileType) {
        try {
            Uploadable fileInfo = new File(userId, multipartFile, fileType);
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
        String targetBucketName = BucketUtils.getBaseBucketName(PROFILE_IMAGE);

        ListObjectsRequest listObjectsRequest
                = new ListObjectsRequest()
                .withBucketName(targetBucketName)
                .withPrefix(folder + "/")
                .withDelimiter("/");

        ObjectListing objectListing = amazonS3.listObjects(listObjectsRequest);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();

        return objectSummaries.stream()
                .map(S3ObjectSummary::getKey)
                .filter(fileName -> !StringUtils.isBlank(fileName))
                .toList();
    }

    public void deleteFile(String bucketName, String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
    }
}
package flab.project.common.FileStorage;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.*;
import flab.project.domain.file.model.File;
import flab.project.domain.file.model.Uploadable;
import flab.project.domain.file.enums.FileType;
import io.micrometer.common.util.StringUtils;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static flab.project.domain.file.enums.FileType.PROFILE_IMAGE;

@RequiredArgsConstructor
@Component
public class FileStorage {

    public static final int EXPIRATION_TIME = 1000 * 60 * 15;
    private final AmazonS3 amazonS3;

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

    public UploadedFileUrls generatePreSignedUrls(long userId, int fileCount, FileType fileType) {
        Set<UploadedFileUrl> uploadedFileUrls = IntStream.range(0, fileCount)
                .mapToObj(i -> generatePreSignedUrl(userId, fileType))
                .collect(Collectors.toSet());

        return new UploadedFileUrls(uploadedFileUrls);
    }

    public UploadedFileUrl generatePreSignedUrl(long userId, FileType fileType) {
        try {
            String bucketName = fileType.getBucketName(userId);
            GeneratePresignedUrlRequest generatePresignedUrlRequest = generatePresignedUrlRequest(bucketName);

            URL preSignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

            return new UploadedFileUrl(preSignedUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GeneratePresignedUrlRequest generatePresignedUrlRequest(String bucketPath) {
        UUID uniqueKey = UUID.randomUUID();
        Date expirationTime = generateExpirationTime();

        GeneratePresignedUrlRequest generatePresignedUrlRequest
                = new GeneratePresignedUrlRequest(bucketPath, uniqueKey.toString())
                .withMethod(HttpMethod.PUT)
                .withExpiration(expirationTime);

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }

    private Date generateExpirationTime() {
        Date expirationTime = new Date();
        long expirationTimeMillis = expirationTime.getTime() + EXPIRATION_TIME;
        expirationTime.setTime(expirationTimeMillis);

        return expirationTime;
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
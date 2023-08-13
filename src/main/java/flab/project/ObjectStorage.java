package flab.project;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import flab.project.config.baseresponse.BaseResponse;
import flab.project.data.file.ProfileImgage;
import flab.project.data.enums.FileType;
import flab.project.data.file.UploadingFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ObjectStorage {

    private final AmazonS3 amazonS3;
    private final FileFactory fileFactory;

    public String uploadFile(long userId, MultipartFile multipartFile, FileType fileType) {

        try {
            UploadingFile fileInfo = fileFactory.getFileInfo(userId, multipartFile, fileType);
            String bucketPath = fileInfo.getBucketName()+"/";
            String fileName = fileInfo.getFileName();
            ObjectMetadata objectMetadata = fileInfo.getObjectMetadata();

            amazonS3.putObject(
                    bucketPath,
                    fileName,
                    multipartFile.getInputStream(),
                    objectMetadata
            );

            return amazonS3.getUrl(bucketPath, fileName).toString();
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

    public List<String> getFileNamesInBucket(long userId) {
        String folder = "" + userId;
        String targetBucketName = ProfileImgage.BASE_BUCKET_NAME;


        ListObjectsRequest listObjectsRequest
                = new ListObjectsRequest()
                .withBucketName(targetBucketName)
                .withPrefix(folder + "/")
                .withDelimiter("/");

        ObjectListing objectListing = amazonS3.listObjects(listObjectsRequest);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();

        List<String> fileNames = objectSummaries.stream()
                .map(S3ObjectSummary::getKey)
//                .map(file -> file.substring(file.lastIndexOf("/") + 1))
                .filter(fileName -> fileName.trim().length()!=0)
                .collect(Collectors.toList());

        for (String fileName : fileNames) {
            System.out.println("fileName = " + fileName);
        }

        return fileNames;
    }

    public void deleteProfileImage(String fileName) {
        amazonS3.deleteObject(ProfileImgage.BASE_BUCKET_NAME, fileName);
    }

    public void getFiles() {
    }

    public void deleteFiles() {
    }
}

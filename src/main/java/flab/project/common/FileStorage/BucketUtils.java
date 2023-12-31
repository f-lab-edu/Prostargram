package flab.project.common.FileStorage;

import flab.project.data.enums.FileType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BucketUtils {

    private static final String PROFILE_IMAGE_BASE_BUCKET_NAME = "profileimage";
    private static final String POST_IMAGE_BASE_BUCKET_NAME = "postimage";

    public static String getBaseBucketName(FileType fileType) {
        return switch (fileType) {
            case PROFILE_IMAGE -> PROFILE_IMAGE_BASE_BUCKET_NAME;
            case POST_IMAGE -> POST_IMAGE_BASE_BUCKET_NAME;
        };
    }
}
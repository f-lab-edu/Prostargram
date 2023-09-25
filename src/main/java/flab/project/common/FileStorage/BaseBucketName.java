package flab.project.common.FileStorage;

import flab.project.data.enums.FileType;
import flab.project.data.enums.PostType;

import static flab.project.data.enums.FileType.POST_IMAGE;
import static flab.project.data.enums.FileType.PROFILE_IMAGE;

public class BaseBucketName {

    private static final String PROFILE_IMAGE_BASE_BUCKET_NAME = "profileimage";
    private static final String POST_IMAGE_BASE_BUCEKT_NAME = "postimage";

    public static String getBaseBucektName(FileType fileType) {
        return switch (fileType) {
            case PROFILE_IMAGE -> PROFILE_IMAGE_BASE_BUCKET_NAME;
            case POST_IMAGE -> POST_IMAGE_BASE_BUCEKT_NAME;
        };
    }
}
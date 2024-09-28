package flab.project.domain.file.enums;

public enum FileType {
    PROFILE_IMAGE("profileimage"),
    POST_IMAGE("postimage");

    String bucketName;

    FileType(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBaseBucketName(){
        return bucketName;
    }

    public String getBucketName(long suffix) {
        return bucketName + "/" + suffix;
    }
}

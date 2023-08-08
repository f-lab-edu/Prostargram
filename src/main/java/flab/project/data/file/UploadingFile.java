package flab.project.data.file;

import com.amazonaws.services.s3.model.ObjectMetadata;

public interface UploadingFile {

    public String getBucketName();

    public String getFileName();

    public ObjectMetadata getObjectMetadata();

}

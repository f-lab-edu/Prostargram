package flab.project.data.dto.file;

import com.amazonaws.services.s3.model.ObjectMetadata;

public interface Uploadable {
    public String getBucketName();

    public String getFileName();

    public ObjectMetadata getObjectMetadata();
}

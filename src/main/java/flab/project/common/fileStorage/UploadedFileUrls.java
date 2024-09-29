package flab.project.common.fileStorage;

import java.util.Set;
import java.util.stream.Collectors;

public class UploadedFileUrls {
    private final Set<UploadedFileUrl> uploadedFileUrl;

    public UploadedFileUrls(Set<UploadedFileUrl> uploadedFileUrl) {
        this.uploadedFileUrl = uploadedFileUrl;
    }

    public Set<String> getPreSignedUrls() {
        return uploadedFileUrl.stream()
                .map(UploadedFileUrl::getPreSignedUrl)
                .collect(Collectors.toSet());
    }

    public Set<String> getContentUrls() {
        return uploadedFileUrl.stream()
                .map(UploadedFileUrl::getContentUrl)
                .collect(Collectors.toSet());
    }
}

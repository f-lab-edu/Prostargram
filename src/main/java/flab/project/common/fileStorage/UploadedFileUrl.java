package flab.project.common.fileStorage;

import java.net.URL;

public class UploadedFileUrl {

    private final URL preSignedUrl;

    public UploadedFileUrl(URL preSignedUrl) {
        this.preSignedUrl = preSignedUrl;
    }

    public String getPreSignedUrl() {
        return preSignedUrl.toString();
    }

    public String getContentUrl() {
        return String.format(
                "%s://%s%s",
                preSignedUrl.getProtocol(),
                preSignedUrl.getAuthority(),
                preSignedUrl.getPath()
        );
    }
}

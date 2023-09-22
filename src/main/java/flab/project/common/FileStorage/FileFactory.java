package flab.project.common.FileStorage;

import flab.project.data.dto.file.File;
import flab.project.data.dto.file.Uploadable;
import flab.project.data.enums.FileType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static flab.project.data.enums.FileType.POST_IMAGE;
import static flab.project.data.enums.FileType.PROFILE_IMAGE;

@Component
public class FileFactory {

    public Uploadable getFileInfo(long userId, MultipartFile file, FileType fileType) {
        if (fileType.equals(PROFILE_IMAGE)) {
            return new File(userId, file, fileType);
        } else if (fileType.equals(POST_IMAGE)) {
            return null;
        } else {
            throw new IllegalArgumentException();
        }
    }
}

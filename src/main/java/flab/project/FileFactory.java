package flab.project;

import static flab.project.data.enums.FileType.POST_IMAGE;
import static flab.project.data.enums.FileType.PROFILE_IMAGE;

import flab.project.data.enums.FileType;
import flab.project.data.file.ProfileImgage;
import flab.project.data.file.UploadingFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileFactory {

    public UploadingFile getFileInfo(long userId, MultipartFile file, FileType fileType) {
        if (fileType.equals(PROFILE_IMAGE)) {
            return new ProfileImgage(userId, file);
        }else if (fileType.equals(POST_IMAGE)){
            return null;
        }else{
            throw new IllegalArgumentException();
        }
    }

}

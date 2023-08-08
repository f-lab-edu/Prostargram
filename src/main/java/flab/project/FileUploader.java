package flab.project;

import flab.project.data.enums.FileType;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {

    public String uploadFile(long userId, List<MultipartFile> multipartFile, FileType fileType);

    public void getFiles();

    public void deleteFiles();
}

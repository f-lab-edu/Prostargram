package flab.project.domain.file.model;

import flab.project.domain.file.enums.FileType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageUploadRequest {

    private int imageCount;
    private FileType fileType;
}

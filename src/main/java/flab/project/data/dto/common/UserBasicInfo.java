package flab.project.data.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public abstract class UserBasicInfo {
    @Schema(example = "1")
    private Long userId;

    @Schema(example = "정민욱")
    private String userName;

    @Schema(example = "https://profileImg.url")
    private String profileImgUrl;

    @Schema(example = "카카오")
    private String departmentName;
}

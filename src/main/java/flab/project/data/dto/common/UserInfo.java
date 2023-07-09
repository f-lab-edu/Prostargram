package flab.project.data.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public abstract class UserInfo {

    @Schema(example = "카카오")
    private String departmentName;
}

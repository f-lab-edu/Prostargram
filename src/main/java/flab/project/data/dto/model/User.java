package flab.project.data.dto.model;

import flab.project.data.dto.model.BasicUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class User extends BasicUser {

    @Schema(example = "카카오")
    private String departmentName;

}

package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "프로필 페이지와 프로필 수정 페이지의 공통 Schema")
public class Profile extends User {

    @Schema(example = "백엔드 엔지니어 정민욱입니다.")
    private String selfIntroduction;

    @Schema(example = "[\"http://github.com\",\"http://blog.com]\"")
    private List<String> associatedLinks;
}

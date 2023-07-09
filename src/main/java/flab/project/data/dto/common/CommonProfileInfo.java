package flab.project.data.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "프로필 페이지와 프로필 수정 페이지의 공통 Schema")
public abstract class CommonProfileInfo {
    private Long userId;
    private String userName;
    private String profileImgUrl;
    private String departmentName;
    private String selfIntroduction;
    private List<String> associatedLinkList;
}

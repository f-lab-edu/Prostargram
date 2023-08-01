package flab.project.data.dto.model;

import flab.project.data.dto.User;
import flab.project.data.dto.model.HashTag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;

import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Getter
@Schema(description = "프로필 페이지와 프로필 수정 페이지의 공통 Schema")
public class Profile extends User {
    private final int TOTAL_SOCIAL_ACCOUNT_MAX_SIZE = 3;
    private final int TOTAL_INTEREST_MAX_SIZE = 10;

    @Schema(example = "백엔드 엔지니어 정민욱입니다.")
    private String selfIntroduction;

    @Size(max = TOTAL_SOCIAL_ACCOUNT_MAX_SIZE)
    @Schema(example = "[\"http://github.com\",\"http://blog.com\"]")
    private List<String> socialAccounts;

    @Size(max = TOTAL_INTEREST_MAX_SIZE)
    @Schema(example = "[\"#aws\",\"#java\"]")
    private List<String> interests;

    public boolean hasProfileFiled() {
        return StringUtils.hasText(getSelfIntroduction())
            || StringUtils.hasText(getUserName())
            || StringUtils.hasText(getProfileImgUrl());
    }

    public boolean hasSocialAccounts() {
        return !CollectionUtils.isEmpty(getSocialAccounts());
    }
}

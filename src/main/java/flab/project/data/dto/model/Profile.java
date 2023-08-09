package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import lombok.Getter;

import java.util.List;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Getter
@Schema(description = "프로필 페이지와 프로필 수정 페이지의 공통 Schema")
public class Profile extends User {
    private static final int TOTAL_SOCIAL_ACCOUNT_MAX_SIZE = 3;
    private static final int TOTAL_INTEREST_MAX_SIZE = 10;
    private static final int TARGET_OF_BAD_WORD_FILTER_MAX_SIZE =16;
    private static final int INTEREST_MAX_LENGTH=15;
    @Schema(example = "백엔드 엔지니어 정민욱입니다.")
    private String selfIntroduction;

    @Size(max = TOTAL_SOCIAL_ACCOUNT_MAX_SIZE)
    @Schema(example = "[\"http://github.com\",\"http://blog.com\"]")
    private List<String> socialAccounts;

    @Size(max = TOTAL_INTEREST_MAX_SIZE)
    @Schema(example = "[\"#aws\",\"#java\"]")
    private List<@Pattern(regexp = "^#.*") @Length(max = INTEREST_MAX_LENGTH) @NotBlank String> interests;

    public boolean hasProfileField() {
        return StringUtils.hasText(getSelfIntroduction())
            || StringUtils.hasText(getUserName())
            || StringUtils.hasText(getProfileImgUrl());
    }

    public boolean hasSocialAccounts() {
        return !CollectionUtils.isEmpty(getSocialAccounts());
    }

    public List<String> getStringFields() {

        List<String> targets = new ArrayList<>(TARGET_OF_BAD_WORD_FILTER_MAX_SIZE);

        targets.add(selfIntroduction);
        targets.add(departmentName);
        targets.add(userName);

        targets.addAll(socialAccounts);
        targets.addAll(interests);

        return targets;
    }

    public void setProfileImg(String imageUrl) {
        super.profileImgUrl = imageUrl;
    }

    public void convertInterestsToLowerCase() {
        if (CollectionUtils.isEmpty(interests)) {
            return ;
        }

        interests.forEach(String::toLowerCase);
    }
}

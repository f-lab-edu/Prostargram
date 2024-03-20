package flab.project.data.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "프로필 페이지와 프로필 수정 페이지의 공통 Schema")
public class Profile extends User {

    @Schema(example = "백엔드 엔지니어 정민욱입니다.")
    private String selfIntroduction;

    @Schema(example = "[\"http://github.com\",\"http://blog.com]\"")
    private List<SocialAccountResponse> socialAccounts;

    @Schema(example = "[\"#aws\",\"#java\"]")
    private List<HashTag> interests;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(example = "100")
    private Long postCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(example = "100")
    private Long followerCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(example = "100")
    private Long followingCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Profile profile = (Profile) o;
        return Objects.equals(selfIntroduction, profile.selfIntroduction) && Objects.equals(
            socialAccounts, profile.socialAccounts) && Objects.equals(interests, profile.interests)
            && Objects.equals(postCount, profile.postCount) && Objects.equals(followerCount,
            profile.followerCount) && Objects.equals(followingCount, profile.followingCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), selfIntroduction, socialAccounts, interests, postCount, followerCount,
            followingCount);
    }
}
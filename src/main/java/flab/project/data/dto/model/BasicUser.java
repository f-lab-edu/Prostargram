package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BasicUser {

    @Schema(example = "1")
    private long userId;

    @Schema(example = "정민욱")
    private String userName;

    @Schema(example = "https://profileImg.url")
    private String profileImgUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicUser basicUser = (BasicUser) o;
        return userId == basicUser.userId && Objects.equals(userName, basicUser.userName)
            && Objects.equals(profileImgUrl, basicUser.profileImgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, profileImgUrl);
    }
}
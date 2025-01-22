package flab.project.domain.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfilePage extends Profile {

    @JsonProperty("isFollow")
    @Schema(example = "false")
    boolean follow;

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
        ProfilePage that = (ProfilePage) o;
        return follow == that.follow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), follow);
    }
}

package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;
import lombok.Getter;

@Getter
public class User extends BasicUser {

    @Schema(example = "카카오")
    private String departmentName;

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
        User user = (User) o;
        return Objects.equals(departmentName, user.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), departmentName);
    }
}
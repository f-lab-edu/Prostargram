package flab.project.data.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequestDto {

    @Length(max = 16)
    @Pattern(regexp = "^[a-zA-Z가-힣0-9_.]+$")
    private String userName;

    @Length(max = 18)
    private String departmentName;

    @Length(max = 200)
    private String selfIntroduction;
}

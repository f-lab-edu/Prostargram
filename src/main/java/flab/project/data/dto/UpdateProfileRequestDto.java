package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
    @Schema(example = "정민욱")
    private String userName;

    @Length(max = 18)
    @Schema(example = "카카오 재직 중")
    private String departmentName;

    @Length(max = 200)
    @Schema(example = "조금씩 조금씩 성장합시다")
    private String selfIntroduction;
}

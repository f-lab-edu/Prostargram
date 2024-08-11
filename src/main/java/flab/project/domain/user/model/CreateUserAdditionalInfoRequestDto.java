package flab.project.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "회원 가입 - 관심사 Dto")
public class CreateUserAdditionalInfoRequestDto {

    @Schema(description = "유저의 ID")
    private long userId;

    @Schema(example = "https://github.com/example", description = "유저의 프로필 하단에 나타날 링크")
    @Size(max = 3)
    private List<AddSocialAccountDto> socialAccounts = new ArrayList<>();

    @Schema(example = "#java", description = "추천 관심사")
    private List<String> recommendInterests = new ArrayList<>();

    @Schema(example = "#spring", description = "나만의 관심사")
    @Size(max = 10)
    private List<AddCustomInterestDto> hashTags = new ArrayList<>();
}

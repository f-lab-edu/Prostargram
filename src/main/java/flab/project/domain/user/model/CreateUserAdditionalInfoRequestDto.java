package flab.project.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "회원가입 2단계 - SocialAccounts 및 interests")
public class CreateUserAdditionalInfoRequestDto {

    @Schema(type ="array", description = "유저의 프로필 하단에 나타날 링크")
    @Size(max = 3)
    private List<AddSocialAccountDto> socialAccounts = new ArrayList<>();

    @Schema(type = "array", description = "추천 관심사")
    private List<String> recommendInterests = new ArrayList<>();

    @Schema(type ="array", description = "나만의 관심사")
    @Size(max = 10)
    private List<AddCustomInterestDto> customInterests = new ArrayList<>();
}

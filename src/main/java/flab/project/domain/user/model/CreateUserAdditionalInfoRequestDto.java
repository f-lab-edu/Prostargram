package flab.project.domain.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "회원 가입 - 관심사 Dto")
public class CreateUserAdditionalInfoRequestDto {

    @Schema(example = "https://github.com/example", description = "유저의 프로필 하단에 나타날 링크")
    private final List<String> socialAccounts;

    @Schema(example = "#java", description = "나만의 관심사")
    private final List<String> hashTags;

    public CreateUserAdditionalInfoRequestDto(List<String> socialAccounts, List<String> hashTags) {
        this.socialAccounts = socialAccounts;
        this.hashTags = hashTags;
    }
}

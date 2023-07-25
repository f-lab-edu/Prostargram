package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "회원 가입 - 관심사 Dto")
public class CreateUser2RequestDto {

    @Schema(example = "https://github.com/example", description = "유저의 프로필 하단에 나타날 링크")
    private final List<String> associatedLink;

    @Schema(example = "['#java', '#javascript', 'aws', 'react', 'git']", description = "추천 관심사")
    private final List<String> recommendHashTags;

    @Schema(example = "#java", description = "나만의 관심사")
    private final List<String> hashTags;

    public CreateUser2RequestDto(List<String> associatedLink, List<String> recommendHashTags, List<String> hashTags) {
        this.associatedLink = associatedLink;
        this.recommendHashTags = recommendHashTags;
        this.hashTags = hashTags;
    }
}
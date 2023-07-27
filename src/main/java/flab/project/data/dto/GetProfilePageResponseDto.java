package flab.project.data.dto;

import flab.project.data.dto.domain.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "프로필 페이지 요청때 반환되는 Schema")
public class GetProfilePageResponseDto extends Profile {

    @Schema(example = "100")
    private long postCount;

    @Schema(example = "100")
    private long follwerCount;

    @Schema(example = "100")
    private long followingCount;
}

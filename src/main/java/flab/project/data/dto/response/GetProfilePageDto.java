package flab.project.data.dto.response;

import flab.project.data.dto.common.ProfileInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "프로필 페이지 요청때 반환되는 Schema")
public class GetProfilePageDto extends ProfileInfo {

    @Schema(example = "100")
    private long postCount;

    @Schema(example = "100")
    private long follwerCount;

    @Schema(example = "100")
    private long followingCount;
}

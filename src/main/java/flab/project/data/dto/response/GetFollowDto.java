package flab.project.data.dto.response;

import flab.project.data.dto.common.UserBasicInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "팔로워/팔로잉 정보를 담는 Dto")
public class GetFollowDto extends UserBasicInfo {
    @Schema(description = "FOLLOW 테이블의 followId를 의미함.")
    private Long followId;
}

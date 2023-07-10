package flab.project.data.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import flab.project.data.dto.model.UserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"followId", "userId", "userName", "profileImgUrl", "departmentName" })
@Schema(description = "팔로워/팔로잉 정보를 담는 Dto")
public class GetFollowDto extends UserInfo {
    @Schema(description = "FOLLOW 테이블의 followId를 의미함.")
    private long followId;
}

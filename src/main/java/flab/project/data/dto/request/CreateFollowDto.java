package flab.project.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "팔로워/팔로잉 추가 DTO")
public class CreateFollowDto {

    @Schema(example = "10")
    private long fromUserId;

    @Schema(example = "20")
    private long toUserId;
}

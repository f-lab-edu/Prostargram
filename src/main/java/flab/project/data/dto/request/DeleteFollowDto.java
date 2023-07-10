package flab.project.data.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "팔로워/팔로잉 삭제 DTO")
public class DeleteFollowDto {

    @Schema(example = "20")
    private long followId;
}

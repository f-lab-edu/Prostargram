package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "팔로워/팔로잉 추가 DTO")
public class Follows {

    @Positive
    @Schema(example = "10")
    private final long fromUserId;

    @Positive
    @Schema(example = "20")
    private final long toUserId;

}

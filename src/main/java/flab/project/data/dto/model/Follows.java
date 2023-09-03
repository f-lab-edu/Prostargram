package flab.project.data.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "팔로워/팔로잉 추가 DTO")
public class Follows {

    @Positive
    @Schema(example = "10")
    @JsonProperty("fromUserId")
    private long fromUserId;

    @Positive
    @Schema(example = "20")
    @JsonProperty("toUserId")
    private long toUserId;

}

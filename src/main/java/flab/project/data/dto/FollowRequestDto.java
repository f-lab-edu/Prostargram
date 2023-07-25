package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "팔로워/팔로잉 추가 DTO")
public class FollowRequestDto {

    @Positive
    @Schema(example = "10")
    private final long fromUserId;

    @Positive
    @Schema(example = "20")
    private final long toUserId;

    //todo fromUserId와 toUserId가 서로 다른 값인지를 확인하는 로직이 Service단의 private메서드에 안들어가고 DTO에 들어가는게 맞을까?
    public boolean isEqaulFromUserIdAndToUserId() {
        return getFromUserId() == getToUserId();
    }
}

package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSocialAccountRequestDto {
    @Positive
    private long userId;

    //todo 유저가 피싱 사이트를 올리면 어떡하지? https://로 시작하는 주소만 올릴 수 있게하면 처리할 수 있을까?
    @Schema(example = "http://github.com")
    private String socialAccountUrl;
}

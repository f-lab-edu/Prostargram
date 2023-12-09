package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicUser {

    @Schema(example = "1")
    private long userId;

    @Schema(example = "정민욱")
    private String userName;

    @Schema(example = "https://profileImg.url")
    private String profileImgUrl;
}
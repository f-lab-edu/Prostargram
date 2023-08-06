package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class BasicUser {

    @Schema(example = "1")
    protected long userId;

    @Schema(example = "정민욱")
    protected String userName;

    @Schema(example = "https://profileImg.url")
    protected String profileImgUrl;

}


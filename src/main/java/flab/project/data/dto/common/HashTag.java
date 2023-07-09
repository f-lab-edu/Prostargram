package flab.project.data.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;

public class HashTag {

    @Schema(example = "1")
    private Long hashTagId;

    @Schema(example = "#aws")
    private String hashTagName;
}

package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class HashTag {

    @Schema(example = "1")
    private long hashTagId;

    @Schema(example = "#aws")
    private String hashTagName;
}

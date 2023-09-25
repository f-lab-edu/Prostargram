package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HashTag {

    @Schema(example = "1")
    private long hashTagId;

    @Schema(example = "#aws")
    private String hashTagName;

    public HashTag(String hashTagName) {
        this.hashTagName = hashTagName;
    }
}

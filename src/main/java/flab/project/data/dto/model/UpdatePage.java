package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class UpdatePage extends Profile {

    @Schema(example = "[\"#aws\",\"#java\"]")
    private List<HashTag> hashTags;
}

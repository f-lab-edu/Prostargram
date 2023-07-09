package flab.project.data.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class UpdatePage extends ProfileInfo {

    @Schema(example = "해시태그 리스트")
    private List<HashTag> hashTagList;
}

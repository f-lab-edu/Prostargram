package flab.project.data.dto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
@Schema(description = "통계 게시물 Dto")
public class PollPost extends BasePost {

    private List<Option> options;

    @Schema(example = "2023-07-01")
    private LocalDate startDate;

    @Schema(example = "2023-07-14")
    private LocalDate endDate;
}

package flab.project.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Schema(description = "통계 피드 Dto")
public class PollPost extends BasePost {

    @Schema(example = "[2, 3, 4, 5]")
    private List<Long> options;

    @Schema(example = "2023-07-01")
    private LocalDate startDate;

    @Schema(example = "2023-07-14")
    private LocalDate endDate;

}

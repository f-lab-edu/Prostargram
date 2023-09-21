package flab.project.data.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PollPeriod {

    private LocalDate startDate;

    private LocalDate endDate;
}

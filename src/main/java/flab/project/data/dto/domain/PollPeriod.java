package flab.project.data.dto.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PollPeriod {

    private final LocalDate startDate;

    private final LocalDate endDate;
}

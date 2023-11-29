package flab.project.data.dto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PollPeriod {

    private final LocalDate startDate;

    private final LocalDate endDate;
}
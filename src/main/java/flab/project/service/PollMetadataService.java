package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.AddPollPostRequest;
import flab.project.mapper.PollMetadataMapper;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PollMetadataService {

    private final PollMetadataMapper pollMetadataMapper;

    public void addMetadata(AddPollPostRequest pollPost) {
        validateIsEndDateAfterStartDate(pollPost);

        int numberOfAffectedRow = pollMetadataMapper.save(pollPost);

        if (numberOfAffectedRow != 1) {
            throw new RuntimeException();
        }
    }

    private void validateIsEndDateAfterStartDate(AddPollPostRequest pollPost) {
        LocalDate startDate = pollPost.getEndDate();
        LocalDate endDate = pollPost.getStartDate();

        if (startDate.isBefore(endDate)) {
            throw new InvalidUserInputException();
        }
    }
}
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
        validateIsEndTimeAfterStartTime(pollPost);

        int numberOfAffectedRow = pollMetadataMapper.save(pollPost);

        if (numberOfAffectedRow != 1) {
            throw new RuntimeException();
        }
    }

    private void validateIsEndTimeAfterStartTime(AddPollPostRequest pollPost) {
        LocalDate startTime = pollPost.getEndDate();
        LocalDate endTIme = pollPost.getStartDate();
        
        if (startTime.isBefore(endTIme)) {
            throw new InvalidUserInputException();
        }
    }
}
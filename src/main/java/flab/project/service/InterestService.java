package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.mapper.InterestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InterestService {

    private final InterestMapper interestMapper;

    public int getNumberOfExistingInterests(long userId) {
        return interestMapper.getNumberOfExistingInterests(userId);
    }

    public void addInterest(long userId, long hashtagId) {
        interestMapper.save(userId, hashtagId);
    }

    public void deleteInterest(long userId, long hashtagId) {
        int numberOfDeletedRow = interestMapper.delete(userId, hashtagId);

        if (numberOfDeletedRow == 0) {
            throw new InvalidUserInputException();
        }
    }
}

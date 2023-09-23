package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
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

    public void addInterest(long userId, long hashTagId) {
        interestMapper.save(userId, hashTagId);
    }

    public void deleteInterest(long userId, long hashTagId) {
        int numberOfDeletedRow = interestMapper.delete(userId, hashTagId);

        if (numberOfDeletedRow == 0) {
            throw new InvalidUserInputException();
        }
    }
}
package flab.project.domain.user.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.mapper.InterestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InterestService {

    private final InterestMapper interestMapper;

    public int getNumberOfExistingInterests(long userId) {
        return interestMapper.getNumberOfExistingInterests(userId);
    }

    public void addInterest(long userId, long hashTagId, String name) {
        interestMapper.save(userId, hashTagId, name);
    }

    public void deleteInterest(long userId, long hashTagId, String name) {
        int numberOfDeletedRow = interestMapper.delete(userId, hashTagId, name);

        if (numberOfDeletedRow == 0) {
            throw new InvalidUserInputException();
        }
    }
}
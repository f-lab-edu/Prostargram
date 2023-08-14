package flab.project.service;

import flab.project.data.dto.AddInterest;
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
}

package flab.project.service;

import flab.project.data.InterestsDelta;
import flab.project.mapper.InterestMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
public class InterestService {

    private final InterestMapper interestMapper;

    public InterestsDelta getInterestDelta(long userId, List<String> receivedInterests) {
        if (CollectionUtils.isEmpty(receivedInterests)) {
            return null;
        }

        List<String> existingInterests = interestMapper.findAllByUserId(userId);

        return new InterestsDelta(existingInterests, receivedInterests);
    }

    public void updateInterests(long userId, InterestsDelta interestsDelta) {
        if (interestsDelta == null) {
            return ;
        }

        if (interestsDelta.hasToAddInterests()) {
            List<String> toAddInterests = interestsDelta.getToAddInterests();
            interestMapper.insertAllIn(userId, toAddInterests);
        }

        if (interestsDelta.hasToDeleteInterests()) {
            List<String> toDeleteInterests = interestsDelta.getToDeleteInterests();
            interestMapper.deleteAllIn(userId, toDeleteInterests);
        }
    }
}

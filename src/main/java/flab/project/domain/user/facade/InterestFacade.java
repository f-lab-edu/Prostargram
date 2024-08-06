package flab.project.domain.user.facade;

import flab.project.common.BadWordChecker;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.domain.user.model.AddInterest;
import flab.project.domain.post.model.HashTag;
import flab.project.domain.post.service.HashTagService;
import flab.project.domain.user.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.project.config.Constraint.NUMBER_LIMIT_OF_INTEREST;

@RequiredArgsConstructor
@Service
public class InterestFacade {

    private final BadWordChecker badWordChecker;
    private final InterestService interestService;
    private final HashTagService hashTagService;

    @Transactional
    public SuccessResponse addInterest(AddInterest addInterestDto) {
        //TODO 추후 해당 로직들을 AOP로 삽입 예정.
        badWordChecker.hasBadWord(addInterestDto.getStringFields());
        validateNumberLimitOfInterest(addInterestDto);

        Long hashTagId = getHashTagIdByHashTagName(addInterestDto);

        interestService.addInterest(addInterestDto.getUserId(), hashTagId);
        return new SuccessResponse();
    }

    public SuccessResponse deleteInterest(long userId, long hashTagId) {
        validateUserIdAndHashTagId(userId, hashTagId);

        interestService.deleteInterest(userId, hashTagId);

        return new SuccessResponse();
    }

    private void validateNumberLimitOfInterest(AddInterest addInterestDto) {
        int numberOfExistingInterests = interestService.getNumberOfExistingInterests(addInterestDto.getUserId());

        if (numberOfExistingInterests > NUMBER_LIMIT_OF_INTEREST) {
            throw new NumberLimitOfInterestExceededException();
        }
    }

    private Long getHashTagIdByHashTagName(AddInterest addInterestDto) {
        Long hashTagId = hashTagService.getHashTagIdByHashtagName(addInterestDto.getInterestNameWithSharp());

        if (hashTagId == null) {
            hashTagId = addHashTag(addInterestDto);
        }
        return hashTagId;
    }

    private Long addHashTag(AddInterest addInterestDto) {
        String interestNameWithSharp = addInterestDto.getInterestNameWithSharp();
        HashTag hashTag = new HashTag(interestNameWithSharp);

        Long hashTagId = hashTagService.addHashTag(hashTag);
        return hashTagId;
    }

    private void validateUserIdAndHashTagId(long userId, long hashTagId) {
        if (userId <= 0 || hashTagId <= 0) {
            throw new InvalidUserInputException();
        }
    }
}
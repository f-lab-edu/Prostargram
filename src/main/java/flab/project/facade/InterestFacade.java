package flab.project.facade;

import flab.project.common.BadWordChecker;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.data.dto.AddInterest;
import flab.project.data.dto.model.HashTag;
import flab.project.service.HashtagService;
import flab.project.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.project.config.Constraint.NUMBER_LIMIT_OF_INTEREST;

@RequiredArgsConstructor
@Service
public class InterestFacade {

    private final BadWordChecker badWordChecker;
    private final InterestService interestService;
    private final HashtagService hashtagService;

    @Transactional
    public SuccessResponse addInterest(AddInterest addInterestDto) {
        //TODO 추후 해당 로직들을 AOP로 삽입 예정.
        badWordChecker.hasBadWord(addInterestDto.getStringFields());

        checkNumberLimitOfInterest(addInterestDto);

        Long hashtagId = hashtagService.getHashtagIdByHashtagName(addInterestDto.getInterestNameWithSharp());

        if (hashtagId == null) {
            String interestNameWithSharp = addInterestDto.getInterestNameWithSharp();
            HashTag hashTag = new HashTag(interestNameWithSharp);
            hashtagId = hashtagService.addHashtag(hashTag);
        }

        interestService.addInterest(addInterestDto.getUserId(), hashtagId);
        return new SuccessResponse();
    }



    public SuccessResponse deleteInterest(long userId, long hashtagId) {

        checkValidation(userId, hashtagId);

        interestService.deleteInterest(userId, hashtagId);

        return new SuccessResponse();
    }

    private void checkNumberLimitOfInterest(AddInterest addInterestDto) {
        int numberOfExistingInterests = interestService.getNumberOfExistingInterests(addInterestDto.getUserId());

        if (numberOfExistingInterests > NUMBER_LIMIT_OF_INTEREST) {
            throw new NumberLimitOfInterestExceededException();
        }
    }

    private void checkValidation(long userId, long hashtagId) {
        if (userId <= 0 || hashtagId <= 0) {
            throw new InvalidUserInputException();
        }
    }
}

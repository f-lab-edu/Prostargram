package flab.project.facade;

import flab.project.common.BadWordChecker;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.data.dto.UpdateInterest;
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
    public SuccessResponse addInterest(UpdateInterest updateInterestDto) {
        //TODO 추후 해당 로직들을 AOP로 삽입 예정.
        badWordChecker.hasBadWord(updateInterestDto.getStringFields());
        updateInterestDto.convertEscapeCharacter();

        checkNumberLimitOfInterest(updateInterestDto);

        Long hashtagId = hashtagService.getHashtagIdByHashtagName(updateInterestDto.getInterestNameWithSharp());

        if (hashtagId == null) {
            String interestNameWithSharp = updateInterestDto.getInterestNameWithSharp();
            HashTag hashTag = new HashTag(interestNameWithSharp);
            hashtagId = hashtagService.addHashtag(hashTag);
        }

        interestService.addInterest(updateInterestDto.getUserId(), hashtagId);
        return new SuccessResponse();
    }

    private void checkNumberLimitOfInterest(UpdateInterest updateInterestDto) {
        int numberOfExistingInterests = interestService.getNumberOfExistingInterests(updateInterestDto.getUserId());

        if (numberOfExistingInterests >= NUMBER_LIMIT_OF_INTEREST) {
            throw new NumberLimitOfInterestExceededException();
        }
    }

    public SuccessResponse deleteInterest(UpdateInterest updateInterestDto) {
        Long hashtagId = hashtagService.getHashtagIdByHashtagName(updateInterestDto.getInterestNameWithSharp());

        if (hashtagId == null) {
            throw new InvalidUserInputException();
        }

        interestService.deleteInterest(updateInterestDto.getUserId(), hashtagId);

        return new SuccessResponse();
    }
}

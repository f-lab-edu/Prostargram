package flab.project.facade;

import flab.project.common.BadWordChecker;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.data.dto.AddInterest;
import flab.project.service.HashtagService;
import flab.project.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.project.data.Constraint.NUMBER_LIMIT_OF_INTEREST;

@RequiredArgsConstructor
@Service
public class InterestFacade {

    private final BadWordChecker badWordChecker;
    private final InterestService interestService;
    private final HashtagService hashtagService;

    @Transactional
    public SuccessResponse addInterest(AddInterest addInterestDto) {
        //TODO 해당 로직들이 AOP로 삽입되게 할 수는 없을까?
        badWordChecker.hasBadWord(addInterestDto.getStringFields());
        addInterestDto.convertEscapeCharacter();

        checkNumberLimitOfInterest(addInterestDto);

        //todo reassigned local variable은 안좋은 걸까?
        Long hashtagId = hashtagService.getHashtagIdByHashtagName(addInterestDto.getInterestNameWithSharp());

        if (isNotExistHashtag(hashtagId)) {
            hashtagId = hashtagService.addHashtag(addInterestDto.getInterestNameWithSharp());
        }

        interestService.addInterest(addInterestDto.getUserId(), hashtagId);
        return new SuccessResponse();
    }

    private void checkNumberLimitOfInterest(AddInterest addInterestDto) {
        int numberOfExistingInterests = interestService.getNumberOfExistingInterests(addInterestDto.getUserId());

        if (numberOfExistingInterests >= NUMBER_LIMIT_OF_INTEREST) { //todo Constraint라는 클래스를 관리하는게 좋은 선택일까? Enum vs Static fields
            throw new NumberLimitOfInterestExceededException();
        }
    }

    private boolean isNotExistHashtag(Long hashtagId) {
        return hashtagId == null;
    }
}

package flab.project.facade;

import flab.project.common.BadWordChecker;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.data.dto.UpdateInterest;
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
    public SuccessResponse addInterest(UpdateInterest updateInterestDto) {
        //TODO 해당 로직들이 AOP로 삽입되게 할 수는 없을까?
        badWordChecker.hasBadWord(updateInterestDto.findStringFields());
        updateInterestDto.convertEscapeCharacter();

        checkNumberLimitOfInterest(updateInterestDto);

        //todo reassigned local variable은 안좋은 걸까?
        Long hashtagId = hashtagService.getHashtagIdByHashtagName(updateInterestDto.findInterestNameWithSharp());

        if (isNotExistHashtag(hashtagId)) {
            hashtagId = hashtagService.addHashtag(updateInterestDto.findInterestNameWithSharp());
        }

        interestService.addInterest(updateInterestDto.getUserId(), hashtagId);
        return new SuccessResponse();
    }

    private void checkNumberLimitOfInterest(UpdateInterest updateInterestDto) {
        int numberOfExistingInterests = interestService.getNumberOfExistingInterests(updateInterestDto.getUserId());

        if (numberOfExistingInterests >= NUMBER_LIMIT_OF_INTEREST) { //todo Constraint라는 클래스를 관리하는게 좋은 선택일까? Enum vs Static fields
            throw new NumberLimitOfInterestExceededException();
        }
    }

    private boolean isNotExistHashtag(Long hashtagId) {
        return hashtagId == null;
    }

    // Todo 아래 삭제 로직이 Facade에 있는게 바람직 할까?
    // Interest Service에서 hashtagService 메서드를 호출하고 그러는 것 보다는 Facade에서 이뤄지는게 자연스러운거 같긴 한데...
    public SuccessResponse deleteInterest(UpdateInterest updateInterestDto) {
        Long hashtagId = hashtagService.getHashtagIdByHashtagName(updateInterestDto.findInterestNameWithSharp());

        if (isNotExistHashtag(hashtagId)){
            throw new InvalidUserInputException();
        }

        interestService.deleteInterest(updateInterestDto.getUserId(), hashtagId);

        return new SuccessResponse();
    }
}

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

        // 관심사 테이블에서 기존 관심사 개수 가져오기.
        int numberOfExistingInterests = interestService.getNumberOfExistingInterests(addInterestDto.getUserId());

        // 기존 관심사 개수가 이미 3이면 throw Exception
        if (numberOfExistingInterests >= NUMBER_LIMIT_OF_INTEREST) { //todo Constraint라는 클래스를 관리하는게 좋은 선택일까? Enum vs Static fields
            throw new NumberLimitOfInterestExceededException();
        }

        //todo reassigned local variable은 안좋은 걸까?
        //해시 태그에 존재 하는 관심사 인지 확인 하기
        Long hashtagId = hashtagService.getHashtagIdByHashtagName(addInterestDto.getInterestNameWithSharp());

        // 해시태그에 존재하지 않는 관심사 라면 해시태그 테이블에 추가하기.
        if (isNotExistHashtag(hashtagId)) {
            hashtagId = hashtagService.addHashtag(addInterestDto.getInterestNameWithSharp());
        }

        // 관심사 테이블에 추가하기
        interestService.addInterest(addInterestDto.getUserId(), hashtagId);
        return new SuccessResponse();
    }

    private boolean isNotExistHashtag(Long hashtagId) {
        return hashtagId == null;
    }
}

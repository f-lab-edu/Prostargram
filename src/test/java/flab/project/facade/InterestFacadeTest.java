package flab.project.facade;

import flab.project.common.BadWordChecker;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.data.dto.AddInterest;
import flab.project.data.dto.model.HashTag;
import flab.project.service.HashtagService;
import flab.project.service.InterestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InterestFacadeTest {

    @InjectMocks
    private InterestFacade interestFacade;
    @Mock
    private InterestService interestService;
    @Mock
    private HashtagService hashtagService;
    @Mock
    private BadWordChecker badWordChecker;


    @DisplayName("관심사는 최대 3개 까지 설정할 수 있다.")
    @Test
    void InterestMaxNumberLimitIsThree() {
        AddInterest addInterest = new AddInterest(1, "test");
        given(interestService.getNumberOfExistingInterests(anyLong()))
                .willReturn(2);

        assertThatCode(() -> interestFacade.addInterest(addInterest))
                .doesNotThrowAnyException();

        given(interestService.getNumberOfExistingInterests(anyLong()))
                .willReturn(4);

        assertThatThrownBy(() -> interestFacade.addInterest(addInterest))
                .isInstanceOf(NumberLimitOfInterestExceededException.class);
    }

    @DisplayName("생성 하려는 관심사가 해시 태그 테이블에 존재 하지 않는 관심사라면 해시태그 테이블에 추가하는 메서드가 호출된다.")
    @Test
    void receivedInterestNameDoesNotExistInHashtagThenAddToHashtagTable() {
        AddInterest addInterest = new AddInterest(1L, "test");
        HashTag hashTag = new HashTag(addInterest.getInterestNameWithSharp());

        given(hashtagService.getHashtagIdByHashtagName(anyString()))
                .willReturn(null);

        interestFacade.addInterest(addInterest);

        ArgumentCaptor<HashTag> captor = ArgumentCaptor.forClass(HashTag.class);
        verify(hashtagService).addHashtag(captor.capture());

        HashTag captoredHashtag = captor.getValue();
        assertThat(captoredHashtag.getHashTagName()).isEqualTo(hashTag.getHashTagName());
    }

    @DisplayName("생성 하려는 관심사가 해시 태그 테이블에 존재 하는 관심사라면 해시태그 테이블에 추가하는 메서드가 호출되지 않는다.")
    @Test
    void receivedInterestNameExistInHashtagThenMethodToAddHashtagTableDoesNotCalled() {
        AddInterest addInterest = new AddInterest(1L, "test");
        HashTag hashTag = new HashTag(addInterest.getInterestNameWithSharp());

        given(hashtagService.getHashtagIdByHashtagName(anyString()))
                .willReturn(1L);

        interestFacade.addInterest(addInterest);

        then(hashtagService).should(never()).addHashtag(hashTag);
    }
}
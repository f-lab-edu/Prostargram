package flab.project.facade;

import flab.project.common.BadWordChecker;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.data.dto.UpdateInterest;
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
        UpdateInterest updateInterest = new UpdateInterest(1, "test");
        given(interestService.getNumberOfExistingInterests(anyLong()))
                .willReturn(2);

        assertThatCode(() -> interestFacade.addInterest(updateInterest))
                .doesNotThrowAnyException();

        given(interestService.getNumberOfExistingInterests(anyLong()))
                .willReturn(4);

        assertThatThrownBy(() -> interestFacade.addInterest(updateInterest))
                .isInstanceOf(NumberLimitOfInterestExceededException.class);
    }

    @DisplayName("생성 하려는 관심사가 해시 태그 테이블에 존재 하지 않는 관심사라면 해시태그 테이블에 추가하는 메서드가 호출된다.")
    @Test
    void receivedInterestNameDoesNotExistInHashtagThenAddToHashtagTable() {
        UpdateInterest updateInterest = new UpdateInterest(1L, "test");
        HashTag hashTag = new HashTag(updateInterest.getInterestNameWithSharp());

        given(hashtagService.getHashtagIdByHashtagName(anyString()))
                .willReturn(null);

        interestFacade.addInterest(updateInterest);

        ArgumentCaptor<HashTag> captor = ArgumentCaptor.forClass(HashTag.class);
        verify(hashtagService).addHashtag(captor.capture());

        HashTag captoredHashtag = captor.getValue();
        assertThat(captoredHashtag.getHashTagName()).isEqualTo(hashTag.getHashTagName());
    }

    @DisplayName("생성 하려는 관심사가 해시 태그 테이블에 존재 하는 관심사라면 해시태그 테이블에 추가하는 메서드가 호출되지 않는다.")
    @Test
    void receivedInterestNameExistInHashtagThenMethodToAddHashtagTableDoesNotCalled() {
        UpdateInterest updateInterest = new UpdateInterest(1L, "test");
        HashTag hashTag = new HashTag(updateInterest.getInterestNameWithSharp());

        given(hashtagService.getHashtagIdByHashtagName(anyString()))
                .willReturn(1L);

        interestFacade.addInterest(updateInterest);

        then(hashtagService).should(never()).addHashtag(hashTag);
    }

    @DisplayName("삭제하려는 관심사가 존재하지 않는 해시태그라면 InvalidUserInputException을 던진다.")
    @Test
    void deleteInterestThrowInvalidUserInputExceptionWhenHashtagDoesNotExist() {
        UpdateInterest updateInterest = new UpdateInterest(1L, "test");
        given(hashtagService.getHashtagIdByHashtagName(anyString()))
                .willReturn(null);

        assertThatThrownBy(() -> interestFacade.deleteInterest(updateInterest))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("삭제하려는 관심사가 존재하는 해시태그라면 InvalidUserInputException이 던져지지 않고 deleteInterest메서드가 호출된다.")
    @Test
    void deleteInterest() {
        UpdateInterest updateInterest = new UpdateInterest(1L, "test");
        given(hashtagService.getHashtagIdByHashtagName(anyString()))
                .willReturn(1L);

        assertThatCode(() -> interestFacade.deleteInterest(updateInterest))
                .doesNotThrowAnyException();
        then(interestService).should().deleteInterest(1L,1L);
    }
}
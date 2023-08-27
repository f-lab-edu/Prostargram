package flab.project.facade;

import flab.project.common.BadWordChecker;
import flab.project.config.exception.NumberLimitOfInterestExceededException;
import flab.project.data.dto.AddInterest;
import flab.project.data.dto.model.HashTag;
import flab.project.service.HashTagService;
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
    private HashTagService hashtagService;
    @Mock
    private BadWordChecker badWordChecker;


    @DisplayName("관심사는 최대 10개까지 설정할 수 있다.")
    @Test
    void InterestMaxNumberLimitIsThree() {
        // given
        AddInterest addInterest = new AddInterest(1, "test");

        given(interestService.getNumberOfExistingInterests(anyLong()))
                .willReturn(10);

        // when & then
        assertThatCode(() -> interestFacade.addInterest(addInterest))
                .doesNotThrowAnyException();

        // given
        given(interestService.getNumberOfExistingInterests(anyLong()))
                .willReturn(11);

        // when & then
        assertThatThrownBy(() -> interestFacade.addInterest(addInterest))
                .isInstanceOf(NumberLimitOfInterestExceededException.class);
    }

    @DisplayName("생성하려는 관심사가 해시 태그 테이블에 존재하지 않는 관심사라면 해시태그 테이블에 추가하는 메서드가 호출된다.")
    @Test
    void receivedInterestNameDoesNotExistInHashtagThenAddToHashtagTable() {
        // given
        AddInterest addInterest = new AddInterest(1L, "test");
        HashTag hashTag = new HashTag(addInterest.getInterestNameWithSharp());

        given(hashtagService.getHashTagIdByHashtagName(anyString()))
                .willReturn(null);

        ArgumentCaptor<HashTag> captor = ArgumentCaptor.forClass(HashTag.class);

        // when
        interestFacade.addInterest(addInterest);

        // then
        then(hashtagService).should().addHashTag(captor.capture());

        // given
        HashTag captoredHashTag = captor.getValue();

        // when & then
        assertThat(captoredHashTag.getHashTagName()).isEqualTo(hashTag.getHashTagName());
    }

    @DisplayName("생성하려는 관심사가 해시 태그 테이블에 존재 하는 관심사라면 해시태그 테이블에 추가하는 메서드가 호출되지 않는다.")
    @Test
    void receivedInterestNameExistInHashtagThenMethodToAddHashtagTableDoesNotCalled() {
        // given
        AddInterest addInterest = new AddInterest(1L, "test");
        HashTag hashTag = new HashTag(addInterest.getInterestNameWithSharp());

        given(hashtagService.getHashTagIdByHashtagName(anyString()))
                .willReturn(1L);

        // when
        interestFacade.addInterest(addInterest);

        // then
        then(hashtagService).should(never()).addHashTag(hashTag);
    }
}
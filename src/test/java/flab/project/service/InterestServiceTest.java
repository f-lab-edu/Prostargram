package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.service.InterestService;
import flab.project.domain.user.mapper.InterestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class InterestServiceTest {

    @InjectMocks
    private InterestService interestService;
    @Mock
    private InterestMapper interestMapper;

    @DisplayName("현재 설정한 관심사 개수를 가져올 수 있다.")
    @Test
    public void getNumberOfExistingInterests() {
        // given
        long userId = 1;

        // when
        interestService.getNumberOfExistingInterests(userId);

        // then
        then(interestMapper).should().getNumberOfExistingInterests(userId);
    }

    @DisplayName("관심사를 설정할 수 있다.")
    @Test
    void addInterest() {
        // given
        long userId = 1L;
        long hashTagId = 1L;
        String name = "java";

        // when
        interestService.addInterest(userId, hashTagId, name);

        // then
        then(interestMapper).should().save(userId, hashTagId, name);
    }

    @DisplayName("관심사를 삭제할 수 있다.")
    @Test
    void deleteInterest() {
        // given
        long userId = 1L;
        long hashTagId = 1L;
        String name = "java";

        given(interestMapper.delete(userId, hashTagId, name))
                .willReturn(1);

        // when & then
        assertThatCode(() -> interestService.deleteInterest(userId, hashTagId, name))
                .doesNotThrowAnyException();

    }

    @DisplayName("삭제된 관심사가 없으면 InvalidUserInput이 발생한다.")
    @Test
    void throwInvalidUserInputWhenDoesNotDeletedAnyRow() {
        // given
        long userId = 1L;
        long hashTagId = 1L;
        String name = "java";

        given(interestMapper.delete(userId, hashTagId, name))
                .willReturn(0);

        // when & then
        assertThatThrownBy(() -> interestService.deleteInterest(userId, hashTagId, name))
                .isInstanceOf(InvalidUserInputException.class);
    }
}

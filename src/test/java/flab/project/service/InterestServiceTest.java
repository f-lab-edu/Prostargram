package flab.project.service;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.mapper.InterestMapper;
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
        interestService.getNumberOfExistingInterests(1L);

        then(interestMapper).should().getNumberOfExistingInterests(1L);
    }

    @DisplayName("관심사를 설정할 수 있다.")
    @Test
    void addInterest() {
        interestService.addInterest(1L, 1L);

        then(interestMapper).should().save(1L, 1L);
    }

    @DisplayName("관심사를 삭제할 수 있다.")
    @Test
    void deleteInterest() {
        given(interestMapper.delete(1L, 1L))
                .willReturn(1);

        assertThatCode(() -> interestService.deleteInterest(1L, 1L))
                .doesNotThrowAnyException();

    }

    @DisplayName("삭제된 관심사가 없으면 InvalidUserInput이 발생한다.")
    @Test
    void throwInvalidUserInputWhenDoesNotDeletedAnyRow() {
        given(interestMapper.delete(1L, 1L))
                .willReturn(0);

        assertThatThrownBy(() -> interestService.deleteInterest(1L, 1L))
                .isInstanceOf(InvalidUserInputException.class);
    }
}

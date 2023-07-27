package flab.project.data.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import flab.project.config.exception.InvalidUserInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FollowRequestDtoTest {

    @DisplayName("fromUserId와 toUserId가 같은 값이면 IllegalArgumentException 예외를 발생시킨다.")
    @Test
    public void FromUserIdAndToUserIdIsEqual(){
        //given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L, 1L);

        //when then
        assertThatThrownBy(() -> followRequestDto.checkFromUserIdAndToUserIdSame())
            .isInstanceOf(InvalidUserInput.class);

    }

    @DisplayName("fromUserId와 toUserId가 다른 값이면 예외가 발생하지 않는다.")
    @Test
    public void FromUserIdAndToUserIdIsDifferent(){
        //given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L, 2L);

        assertDoesNotThrow(() -> followRequestDto.checkFromUserIdAndToUserIdSame());
    }

}
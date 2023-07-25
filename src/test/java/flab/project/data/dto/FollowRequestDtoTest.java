package flab.project.data.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FollowRequestDtoTest {

    @DisplayName("fromUserId와 toUserId가 같은 값이면 true를 반환한다.")
    @Test
    public void FromUserIdAndToUserIdIsEqual(){
        //given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L, 1L);

        //when
        boolean isEqaul = followRequestDto.isEqaulFromUserIdAndToUserId();

        //then
        assertTrue(isEqaul);
    }

    @DisplayName("fromUserId와 toUserId가 다른 값이면 false를 반환한다.")
    @Test
    public void FromUserIdAndToUserIdIsDifferent(){
        //given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L, 2L);

        //when
        boolean isEqaul = followRequestDto.isEqaulFromUserIdAndToUserId();

        //then
        assertFalse(isEqaul);
    }

}
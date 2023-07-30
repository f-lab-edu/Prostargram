package flab.project.service;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Follows;
import flab.project.mapper.FollowMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FollowServiceUnitTest {

    @InjectMocks
    FollowService followService;

    @Mock
    FollowMapper followMapper;

    @DisplayName("팔로워/팔로잉을 추가할 때, followRequestDto의 파라미터가 같은 값이 들어올 경우 InvalidUserInput Exception을 던진다.")
    @Test
    void followRequestDtoParameterIsNotSameInAddFollow(){
        //given
        Follows follows = new Follows(1L, 1L);
        // when then
        assertThatThrownBy(() -> followService.addFollow(follows))
            .isInstanceOf(InvalidUserInputException.class);
    }
    @DisplayName("팔로워/팔로잉을 추가할 때, followRequestDto의 파라미터가 같은 값이 아니면 정상 처리된다.")
    @Test
    void followRequestDtoParameterIsDifferentInAddFollow(){
        //given
        Follows follows = new Follows(1L, 2L);
        // when then
        assertThatCode(()-> followService.addFollow(follows))
            .doesNotThrowAnyException();
    }

    @DisplayName("팔로워/팔로잉을 삭제할 때, followRequestDto의 파라미터가 같은 값이 들어올 경우 InvalidUserInput Exception을 던진다.")
    @Test
    void followRequestDtoParameterIsNotSameInDeleteFollow(){
        //given
        Follows follows = new Follows(1L, 1L);
        // when then
        assertThatThrownBy(() -> followService.deleteFollow(follows))
            .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("팔로워/팔로잉을 삭제할 때, followRequestDto의 파라미터가 같은 값이 아니면 정상 처리된다.")
    @Test
    void followRequestDtoParameterIsDifferentInDeleteFollow(){
        //given
        Follows follows = new Follows(1L, 2L);
        // when then
        assertThatCode(()-> followService.deleteFollow(follows))
            .doesNotThrowAnyException();
    }
}
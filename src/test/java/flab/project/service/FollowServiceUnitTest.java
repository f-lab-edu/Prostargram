package flab.project.service;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.model.Follows;
import flab.project.data.enums.requestparam.GetFollowsType;
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

    //Todo 이렇게 단순하게 Service단에 전달된 파라미터를 그대로 Controller단에 넘기는 경우에도 test가 필요할까?
    @DisplayName("팔로워/팔로잉을 가져올 수 있다.")
    @Test
    void getFollows() {
        long userId = 1;

        followService.getFollows(userId, GetFollowsType.FOLLOWERS);
        followService.getFollows(userId, GetFollowsType.FOLLOWINGS);
        followService.getFollows(userId, GetFollowsType.ALL);

        verify(followMapper, times(1)).findAll(userId, GetFollowsType.FOLLOWERS);
        verify(followMapper, times(1)).findAll(userId, GetFollowsType.FOLLOWINGS);
        verify(followMapper, times(1)).findAll(userId, GetFollowsType.ALL);
    }

    @DisplayName("팔로워/팔로잉을 가져올 때, userId에 음수 나 0이 들어가면 InvalidUserInput Exception이 발생한다.")
    @Test
    void userIdIsMustPositiveWhengetFollows() {
        final long NEGATIVE_USER_ID = -1;
        final long ZERO_USER_ID = 0;

        assertThatThrownBy(() -> followService.getFollows(NEGATIVE_USER_ID, GetFollowsType.FOLLOWERS))
                .isInstanceOf(InvalidUserInputException.class);

        assertThatThrownBy(() -> followService.getFollows(ZERO_USER_ID, GetFollowsType.FOLLOWERS))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("팔로워/팔로잉을 추가할 때, followRequestDto의 파라미터가 같은 값이 들어올 경우 InvalidUserInput Exception을 던진다.")
    @Test
    void followRequestDtoParameterIsNotSameInAddFollow() {
        Follows follows = new Follows(1L, 1L);

        assertThatThrownBy(() -> followService.addFollow(follows))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("팔로워/팔로잉을 추가할 때, followRequestDto의 파라미터가 같은 값이 아니면 정상 처리된다.")
    @Test
    void followRequestDtoParameterIsDifferentInAddFollow() {
        Follows follows = new Follows(1L, 2L);

        followService.addFollow(follows);
        verify(followMapper, times(1)).addFollow(follows);
    }

    @DisplayName("팔로워/팔로잉을 삭제할 때, followRequestDto의 파라미터가 같은 값이 들어올 경우 InvalidUserInput Exception을 던진다.")
    @Test
    void followRequestDtoParameterIsNotSameInDeleteFollow() {
        Follows follows = new Follows(1L, 1L);

        assertThatThrownBy(() -> followService.deleteFollow(follows))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("팔로워/팔로잉을 삭제할 때, followRequestDto의 파라미터가 같은 값이 아니면 정상 처리된다.")
    @Test
    void followRequestDtoParameterIsDifferentInDeleteFollow() {
        //given
        Follows follows = new Follows(1L, 2L);

        followService.deleteFollow(follows);

        // when then
        verify(followMapper, times(1)).deleteFollow(follows);
    }
}
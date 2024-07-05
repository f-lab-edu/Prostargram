package flab.project.service;


import static flab.project.domain.user.enums.GetFollowsType.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

import flab.project.config.exception.InvalidUserInputException;
import flab.project.domain.user.model.Follows;
import flab.project.domain.user.enums.GetFollowsType;
import flab.project.domain.user.service.FollowService;
import flab.project.domain.user.mapper.FollowMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

@ExtendWith(MockitoExtension.class)
class FollowServiceUnitTest {

    @InjectMocks
    FollowService followService;
    @Mock
    FollowMapper followMapper;

    @DisplayName("팔로워/팔로잉을 가져올 수 있다.")
    @Test
    void getFollows() {
        long userId = 1;

        followService.getFollows(userId, FOLLOWERS);
        followService.getFollows(userId, GetFollowsType.FOLLOWINGS);
        followService.getFollows(userId, GetFollowsType.ALL);

        then(followMapper).should().findAll(userId, FOLLOWERS);
        then(followMapper).should().findAll(userId, FOLLOWINGS);
        then(followMapper).should().findAll(userId, ALL);
    }

    @DisplayName("팔로워/팔로잉을 가져올 때, userId에 음수 나 0이 들어가면 InvalidUserInput Exception이 발생한다.")
    @Test
    void userIdIsMustPositiveWhenGetFollows() {
        final long NEGATIVE_USER_ID = -1;
        final long ZERO_USER_ID = 0;

        assertThatThrownBy(() -> followService.getFollows(NEGATIVE_USER_ID, FOLLOWERS))
                .isInstanceOf(InvalidUserInputException.class);

        assertThatThrownBy(() -> followService.getFollows(ZERO_USER_ID, FOLLOWERS))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("팔로워/팔로잉을 추가할 때, followRequestDto의 파라미터가 같은 값이 아니면 정상 처리된다.")
    @Test
    void followRequestDtoParameterIsDifferentInAddFollow() {
        Follows follows = new Follows(1L, 2L);

        followService.addFollow(follows);
        then(followMapper).should().addFollow(follows);
    }

    @DisplayName("팔로워/팔로잉을 추가할 때, followRequestDto의 파라미터가 같은 값이 들어올 경우 InvalidUserInput Exception을 던진다.")
    @Test
    void followRequestDtoParameterIsNotSameInAddFollow() {
        Follows follows = new Follows(1L, 1L);

        assertThatThrownBy(() -> followService.addFollow(follows))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("팔로워/팔로잉을 추가할 때, followMapper가 DuplicateKeyException을 던질 수 있다.")
    @Test
    void followMapperCanThrowDuplicateKeyException() {
        Follows follows = new Follows(1L, 2L);
        given(followMapper.addFollow(any(Follows.class)))
                .willThrow(DuplicateKeyException.class);

        assertThatThrownBy(() -> followService.addFollow(follows))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @DisplayName("팔로워/팔로잉을 추가할 때, followMapper가 DataIntegrityViolationException을 던질 수 있다.")
    @Test
    void followMapperCanThrowDataIntegrityViolationException() {
        Follows follows = new Follows(1L, 2L);
        given(followMapper.addFollow(any(Follows.class)))
                .willThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> followService.addFollow(follows))
                .isInstanceOf(DataIntegrityViolationException.class);
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
        Follows follows = new Follows(1L, 2L);

        followService.deleteFollow(follows);

        then(followMapper).should().deleteFollow(follows);
    }
}
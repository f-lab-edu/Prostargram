package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.config.exception.NotExistUserException;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.config.exception.InvalidUserInputException;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;

    @DisplayName("프로필 페이지 정보를 가져올 수 있다.")
    @Test
    void getProfilePageInfo() {
        // given
        Profile profile = new Profile();

        given(userMapper.getProfileInfo(1L, GetProfileRequestType.GET))
                .willReturn(profile);

        // when
        SuccessResponse<Profile> profileInfo = userService.getProfileInfo(1L, GetProfileRequestType.GET);

        // then
        assertThat(profileInfo.getResult()).isEqualTo(profile);
        then(userMapper).should().getProfileInfo(1L, GetProfileRequestType.GET);
    }

    @DisplayName("프로필 수정 페이지 정보를 가져올 수 있다.")
    @Test
    void getProfileUpdatePageInfo() {
        // given
        Profile profile = new Profile();

        given(userMapper.getProfileInfo(1L, GetProfileRequestType.UPDATE))
                .willReturn(profile);

        // when
        SuccessResponse<Profile> profileInfo = userService.getProfileInfo(1L, GetProfileRequestType.UPDATE);

        // then
        assertThat(profileInfo.getResult()).isEqualTo(profile);
        then(userMapper).should().getProfileInfo(1L, GetProfileRequestType.UPDATE);
    }

    @DisplayName("프로필 정보를 가져올 때, 존재하지 않는 유저인 경우 NotExistUserException을 던진다.")
    @Test
    void throwNotExistUserExceptionWhenUserIdNotExist() {
        // given
        long notExistUserId = 99L;

        given(userMapper.getProfileInfo(notExistUserId, GetProfileRequestType.GET))
                .willReturn(null);

        // when & then
        assertThatThrownBy(
                () -> userService.getProfileInfo(notExistUserId, GetProfileRequestType.GET))
                .isInstanceOf(NotExistUserException.class);
    }

    @DisplayName("프로필 정보를 수정 할 수 있다.")
    @Test
    void updateProfile() {
        // given
        UpdateProfileRequestDto updateProfileRequestDto = new UpdateProfileRequestDto("정민욱", "카카오 재직 중", "조금씩 조금씩 성장합시다");
        long userId = 1;

        given(userMapper.updateProfile(anyLong(), any(UpdateProfileRequestDto.class)))
                .willReturn(1);

        // when
        userService.updateProfile(userId, updateProfileRequestDto);

        // then
        then(userMapper).should().updateProfile(userId, updateProfileRequestDto);
    }

    @DisplayName("userId가 양수가 아닌 값이 전달되었을 경우, InvalidUserInputException이 발생한다.")
    @Test
    void userIdMustBePositiveWhenUpdateProfile() {
        // given
        UpdateProfileRequestDto updateProfileRequestDto = new UpdateProfileRequestDto("정민욱", "카카오 재직 중", "조금씩 조금씩 성장합시다");
        long zeroUserId = 0;
        long negativeUserId = -1;

        // when & then
        assertThatThrownBy(() -> userService.updateProfile(zeroUserId, updateProfileRequestDto))
                .isInstanceOf(InvalidUserInputException.class);

        assertThatThrownBy(() -> userService.updateProfile(negativeUserId, updateProfileRequestDto))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("update Prfoile쿼리가 userTable에 반영되지 않으면 RuntimeException이 발생한다.")
    @Test
    void ifUpdateProfileReturnZeroThrowRuntimeException() {
        // given
        UpdateProfileRequestDto updateProfileRequestDto = new UpdateProfileRequestDto("정민욱", "카카오 재직 중", "조금씩 조금씩 성장합시다");
        long userId = 1;

        given(userMapper.updateProfile(anyLong(), any(UpdateProfileRequestDto.class)))
                .willReturn(0);

        // when & then
        assertThatThrownBy(() -> userService.updateProfile(userId, updateProfileRequestDto))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("프로필 이미지 수정이 데이터 베이스에 반영되었을 때(mapper가 1이상을 반환했을 때), true를 반환한다.")
    @Test
    void reflectedRowNumIsBiggerThanZeroThenReturnTrue(){
        // given
        given(userMapper.updateProfileImage(anyLong(), anyString()))
                .willReturn(1);

        // when
        boolean isSuccess = userService.updateProfileImage(1L, "https://profileImgUrlToUpload.com");

        // then
        assertTrue(isSuccess);
    }

    @DisplayName("프로필 이미지 수정이 데이터 베이스에 반영 실패 했을 때(mapper가 0을 반환했을 때), false를 반환한다.")
    @Test
    void reflectedRowNumIsZeroThenReturnFalse() {
        // given
        given(userMapper.updateProfileImage(anyLong(), anyString()))
                .willReturn(0);

        // when
        boolean isSuccess = userService.updateProfileImage(1L, "https://profileImgUrlToUpload.com");

        // then
        assertFalse(isSuccess);
    }

    @DisplayName("lastProfilePostId가 없을 경우, 즉 처음에 프로필 피드들 가져올 수 있다.")
    @Test
    void getProfileFeed_lastProfilePostIdIsNull() {
        // given
        long userId = 1L;
        Long lastProfilePostId = null;
        long limit = 1L;

        // when
        userService.getProfileFeed(userId, lastProfilePostId, limit);

        // then
        then(userMapper).should().getProfileFeed(userId, lastProfilePostId, limit);
    }

    @DisplayName("프로필 피드를 가져올 수 있다.")
    @Test
    void getProfileFeed() {
        // given
        long userId = 1L;
        Long lastProfilePostId = 11L;
        long limit = 1L;

        // when
        userService.getProfileFeed(userId, lastProfilePostId, limit);

        // then
        then(userMapper).should().getProfileFeed(userId, lastProfilePostId, limit);
    }

    @DisplayName("프로필 피드를 가져올 때, lastProfilePostId의 값이 양수가 아니라면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getProfileFeed_invalidLastProfilePostId() {
        // given
        long userId = 1L;
        Long invalidLastProfilePostId = -1L;
        long limit = 1L;

        // when & then
        assertThatThrownBy(() -> userService.getProfileFeed(userId, invalidLastProfilePostId, limit))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("프로필 피드를 가져올 때, limit의 값이 양수가 아니라면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getProfileFeed_negativeLimit() {
        // given
        long userId = 1L;
        Long lastProfilePostId = null;
        long negativeLimit = -1L;

        // when & then
        assertThatThrownBy(() -> userService.getProfileFeed(userId, lastProfilePostId, negativeLimit))
                .isInstanceOf(InvalidUserInputException.class);
    }

    @DisplayName("프로필 피드를 가져올 때, limit의 값이 10을 초과한다면 InvalidUserInput 예외를 반환한다.")
    @Test
    void getProfileFeed_excessLimit() {
        // given
        long userId = 1L;
        Long lastProfilePostId = null;
        long excessLimit = 11L;

        // when & then
        assertThatThrownBy(() -> userService.getProfileFeed(userId, lastProfilePostId, excessLimit))
                .isInstanceOf(InvalidUserInputException.class);
    }
}
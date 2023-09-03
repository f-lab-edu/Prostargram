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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

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
}
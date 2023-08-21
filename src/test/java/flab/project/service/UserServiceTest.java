package flab.project.service;

import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.dto.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;

    @DisplayName("프로필 페이지 정보를 가져올 수 있다.")
    @Test
    void getProfilePageInfo() {
        Profile profile = new Profile();
        given(userMapper.getProfileInfo(1L, GetProfileRequestType.GET))
                .willReturn(profile);

        SuccessResponse<Profile> profileInfo = userService.getProfileInfo(1L, GetProfileRequestType.GET);

        assertThat(profileInfo.getResult()).isEqualTo(profile);
        then(userMapper).should().getProfileInfo(1L, GetProfileRequestType.GET);
    }

    @DisplayName("프로필 수정 페이지 정보를 가져올 수 있다.")
    @Test
    void getProfileUpdatePageInfo() {
        Profile profile = new Profile();
        given(userMapper.getProfileInfo(1L, GetProfileRequestType.UPDATE))
                .willReturn(profile);

        SuccessResponse<Profile> profileInfo = userService.getProfileInfo(1L, GetProfileRequestType.UPDATE);

        assertThat(profileInfo.getResult()).isEqualTo(profile);
        then(userMapper).should().getProfileInfo(1L, GetProfileRequestType.UPDATE);
    }
}
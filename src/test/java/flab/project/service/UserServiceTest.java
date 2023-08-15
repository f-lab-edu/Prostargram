package flab.project.service;

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
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @DisplayName("프로필 이미지 수정이 데이터 베이스에 반영되었을 때(mapper가 1이상을 반환했을 때), true를 반환한다.")
    @Test
    void reflectedRowNumIsBiggerThanZeroThenReturnTrue(){
        given(userMapper.updateProfileImage(anyLong(), anyString()))
                .willReturn(1);

        boolean isSuccess = userService.updateProfileImage(1L, "https://profileImgUrlToUpload.com");

        assertTrue(isSuccess);
    }

    @DisplayName("프로필 이미지 수정이 데이터 베이스에 반영 실패 했을 때(mapper가 0을 반환했을 때), false를 반환한다.")
    @Test
    void reflectedRowNumIsZeroThenReturnFalse(){
        given(userMapper.updateProfileImage(anyLong(), anyString()))
                .willReturn(0);

        boolean isSuccess = userService.updateProfileImage(1L, "https://profileImgUrlToUpload.com");

        assertFalse(isSuccess);
    }
}
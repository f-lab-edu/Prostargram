package flab.project.service;

import flab.project.config.baseresponse.ResponseEnum;
import flab.project.config.baseresponse.SuccessResponse;
import flab.project.data.enums.PublicScope;
import flab.project.mapper.SettingMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static flab.project.config.baseresponse.ResponseEnum.SUCCESS;
import static flab.project.data.enums.PublicScope.PRIVATE;
import static flab.project.data.enums.PublicScope.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SettingServiceTest {

    @InjectMocks
    private SettingService settingService;
    @Mock
    private SettingMapper settingMapper;

    @DisplayName("계정 공개 여부를 PUBLIC으로 수정할 수 있다.")
    @Test
    void updateUserPublicScopeToPublic(){
        // given
        long userId=1;

        given(settingMapper.updateUserPublicScope(userId,PUBLIC))
                .willReturn(1);

        // when
        SuccessResponse successResponse = settingService.updateUserPublicScope(userId, PUBLIC);

        // then
        assertThat(successResponse)
                .extracting("isSuccess","message","code")
                .containsExactly(SUCCESS.isSuccess(),SUCCESS.getMessage(),SUCCESS.getCode());
    }

    @DisplayName("계정 공개 여부를 PRIVATE으로 수정할 수 있다.")
    @Test
    void updateUserPublicScopeToPrivate(){
        // given
        long userId=1;

        given(settingMapper.updateUserPublicScope(userId,PRIVATE))
                .willReturn(1);

        // when
        SuccessResponse successResponse = settingService.updateUserPublicScope(userId, PRIVATE);

        // then
        assertThat(successResponse)
                .extracting("isSuccess","message","code")
                .containsExactly(SUCCESS.isSuccess(),SUCCESS.getMessage(),SUCCESS.getCode());
    }

    @DisplayName("계정 공개 여부 수정 메서드를 통해 아무런 row도 수정되지 않으면 RuntimeException을 던진다.")
    @Test
    void throwRuntimeExceptionWhenUpdateUserPublicScope(){
        // given
        long userId=1;

        given(settingMapper.updateUserPublicScope(userId,PRIVATE))
                .willReturn(0);

        // when & then
        assertThatThrownBy(()->settingService.updateUserPublicScope(userId,PUBLIC))
                .isInstanceOf(RuntimeException.class);
    }
}
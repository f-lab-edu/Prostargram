package flab.project.domain.user.mapper;

import static flab.project.domain.user.enums.PublicScope.PRIVATE;
import static flab.project.domain.user.enums.PublicScope.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

import flab.project.domain.user.enums.LoginType;
import flab.project.domain.user.model.Settings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class SettingMapperTest {

    @Autowired
    SignUpMapper signUpMapper;

    @Autowired
    SettingMapper settingMapper;

    @DisplayName("회원가입시 유저의 공개 범위는 PUBLIC이다.")
    @Test
    void getPersonalSettingsByUserId() {
        // given
        signUpMapper.addUser("email1", "username1", "password", LoginType.NORMAL);

        // when
        Settings setting = settingMapper.getPersonalSettingsByUserId(1L);

        // then
        assertThat(setting.getPublicScope()).isEqualTo(PUBLIC);
    }

    @DisplayName("유저의 공개 범위를 수정한다.")
    @Test
    void updateUserPublicScope() {
        // given
        signUpMapper.addUser("email1", "username1", "password", LoginType.NORMAL);

        // when
        settingMapper.updateUserPublicScope(1L, PRIVATE);

        // then
        Settings setting = settingMapper.getPersonalSettingsByUserId(1L);
        assertThat(setting.getPublicScope()).isEqualTo(PRIVATE);
    }
}
package flab.project.domain.user.mapper;

import flab.project.domain.user.enums.LoginType;
import flab.project.domain.user.model.UserForAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Rollback(value = false)
@Transactional()
@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class AuthenticationMapperTest {

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private SignUpMapper signUpMapper;

    @DisplayName("email로 유저 인증 정보를 가져온다.")
    @Test
    void getUser() {
        // given
        String email = "no-reply@test.com";
        signUpMapper.addUser(email, "username1", "password", LoginType.NORMAL);

        // when
        UserForAuth user = authenticationMapper.getUser(email);

        // then
        assertThat(user).extracting(UserForAuth::getEmail).isEqualTo(email);
    }
}
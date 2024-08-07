package flab.project.mapper;

import flab.project.domain.user.enums.LoginType;
import flab.project.domain.user.mapper.InterestMapper;
import flab.project.domain.user.mapper.SignUpMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class InterestMapperTest {

    @Autowired
    SignUpMapper signUpMapper;

    @Autowired
    InterestMapper interestMapper;

    @DisplayName("관심사를 조회한다.")
    @Test
    void getNumberOfExistingInterests() {
        // given
        signUpMapper.addUser("email1", "username1", "password", LoginType.NORMAL);
        signUpMapper.addUser("email2", "username2", "password", LoginType.NORMAL);

        interestMapper.save(1L, 1L, "java");
        interestMapper.save(1L, 2L, "spirng");

        // when
        int numberOfInterests = interestMapper.getNumberOfExistingInterests(1L);

        // then
        assertThat(numberOfInterests).isEqualTo(2);
    }

    @DisplayName("관심사를 저장한다.")
    @Test
    void saveInterest() {
        // given
        signUpMapper.addUser("email1", "username1", "password", LoginType.NORMAL);

        // when
        interestMapper.save(1L, 1L, "java");

        // then
        int numberOfInterests = interestMapper.getNumberOfExistingInterests(1L);
        assertThat(numberOfInterests).isEqualTo(1);
    }

    @DisplayName("관심사를 삭제한다.")
    @Test
    void deleteInterest() {
        // given
        signUpMapper.addUser("email1", "username1", "password", LoginType.NORMAL);
        interestMapper.save(1L, 1L, "java");

        // when
        int deletedCount = interestMapper.delete(1L, 1L, "java");

        // then
        assertThat(deletedCount).isEqualTo(1);
        int numberOfInterests = interestMapper.getNumberOfExistingInterests(1L);
        assertThat(numberOfInterests).isEqualTo(0);
    }
}
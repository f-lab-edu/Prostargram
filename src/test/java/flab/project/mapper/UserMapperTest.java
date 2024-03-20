package flab.project.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.LoginType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SignUpMapper signUpMapper;

    @DisplayName("userIds를 통해 유저 정보를 가져올 수 있다.")
    @Test
    void findWhereUserIdIn() {
        //given
        List<Long> userIds = List.of(1L, 2L);

        signUpMapper.addUser("email", "username1", "password", LoginType.NORMAL);
        signUpMapper.addUser("email", "username2", "password", LoginType.NORMAL);

        //when
        List<Profile> basicUsers = userMapper.findWhereUserIdIn(userIds);

        //then
        assertThat(basicUsers).extracting("userId").contains(1L, 2L);
    }
}
package flab.project.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import flab.project.domain.user.enums.GetProfileRequestType;
import flab.project.domain.user.model.BasicUser;
import flab.project.domain.user.model.Profile;
import flab.project.domain.user.enums.LoginType;
import flab.project.domain.user.model.UpdateProfileRequestDto;
import java.util.Set;

import flab.project.domain.user.mapper.SignUpMapper;
import flab.project.domain.user.mapper.UserMapper;
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

    @DisplayName("프로필 이미지를 변경한다.")
    @Test
    void updateProfileImage() {
        // given
        String profileImageUrl = "https://profile.image.url";
        signUpMapper.addUser("email", "username1", "password", LoginType.NORMAL);

        // when
        userMapper.updateProfileImage(1L, profileImageUrl);

        // then
        BasicUser user = userMapper.getBasicUser(1L);
        assertThat(user.getProfileImgUrl()).isEqualTo(profileImageUrl);
    }

    @DisplayName("프로필 정보를 변경한다.")
    @Test
    void updateProfile() {
        // given
        signUpMapper.addUser("email", "username1", "password", LoginType.NORMAL);

        String updatedUsername = "updatedUsername";
        String updatedDepartmentName = "updatedDepartmentName";
        String updatedSelfIntroduction = "updatedSelfIntroduction";
        UpdateProfileRequestDto updateProfileRequestDto = new UpdateProfileRequestDto(
            updatedUsername,
            updatedDepartmentName,
            updatedSelfIntroduction
        );

        // when
        userMapper.updateProfile(1L, updateProfileRequestDto);

        // then
        Profile profile = userMapper.getProfileInfo(1L, GetProfileRequestType.GET);
        assertThat(profile.getUserName()).isEqualTo(updatedUsername);
        assertThat(profile.getDepartmentName()).isEqualTo(updatedDepartmentName);
        assertThat(profile.getSelfIntroduction()).isEqualTo(updatedSelfIntroduction);
    }

    @DisplayName("userIds를 통해 유저 정보를 가져올 수 있다.")
    @Test
    void findWhereUserIdIn() {
        //given
        Set<Long> userIds = Set.of(1L, 2L);

        signUpMapper.addUser("email", "username1", "password", LoginType.NORMAL);
        signUpMapper.addUser("email", "username2", "password", LoginType.NORMAL);

        //when
        Set<Profile> basicUsers = userMapper.findWhereUserIdIn(userIds);

        //then
        assertThat(basicUsers).extracting("userId").contains(1L, 2L);
    }

    @DisplayName("해당 email로 가입된 유저가 존재하는지 여부를 가져온다.")
    @Test
    void existsByEmail() {
        //given
        String email = "no-reply@test.com";
        signUpMapper.addUser(email, "username1", "password", LoginType.NORMAL);

        //when
        boolean exists = userMapper.existsByEmail(email);

        //then
        assertTrue(exists);
    }
}

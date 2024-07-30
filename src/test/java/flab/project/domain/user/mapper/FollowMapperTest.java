package flab.project.domain.user.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import flab.project.domain.user.enums.GetFollowsType;
import flab.project.domain.user.enums.LoginType;
import flab.project.domain.user.model.BasicUser;
import flab.project.domain.user.model.Follows;
import flab.project.domain.user.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class FollowMapperTest {

    @Autowired
    SignUpMapper signUpMapper;

    @Autowired
    FollowMapper followMapper;

    @BeforeEach
    void setUp() {
        signUpMapper.addUser("email1", "username1", "password", LoginType.NORMAL);
        signUpMapper.addUser("email2", "username2", "password", LoginType.NORMAL);
        signUpMapper.addUser("email3", "username3", "password", LoginType.NORMAL);
        signUpMapper.addUser("email4", "username4", "password", LoginType.NORMAL);
    }

    @DisplayName("팔로워들을 조회한다.")
    @Test
    void findAllFollowers() {
        // given
        Follows follows1 = Follows.builder()
            .fromUserId(2L)
            .toUserId(1L)
            .build();

        Follows follows2 = Follows.builder()
            .fromUserId(3L)
            .toUserId(1L)
            .build();

        Follows follows3 = Follows.builder()
            .fromUserId(4L)
            .toUserId(1L)
            .build();

        // when
        followMapper.addFollow(follows1);
        followMapper.addFollow(follows2);
        followMapper.addFollow(follows3);

        // then
        List<User> followers = followMapper.findAll(1L, GetFollowsType.FOLLOWERS);
        assertThat(followers).extracting(BasicUser::getUserId).hasSize(3).containsExactly(2L, 3L, 4L);
    }

    @DisplayName("팔로잉들을 조회한다.")
    @Test
    void findAllFollowings() {
        // given
        Follows follows1 = Follows.builder()
            .fromUserId(1L)
            .toUserId(2L)
            .build();

        Follows follows2 = Follows.builder()
            .fromUserId(1L)
            .toUserId(3L)
            .build();

        Follows follows3 = Follows.builder()
            .fromUserId(1L)
            .toUserId(4L)
            .build();

        // when
        followMapper.addFollow(follows1);
        followMapper.addFollow(follows2);
        followMapper.addFollow(follows3);

        // then
        List<User> followings = followMapper.findAll(1L, GetFollowsType.FOLLOWINGS);
        assertThat(followings).extracting(BasicUser::getUserId).hasSize(3).containsExactly(2L, 3L, 4L);
    }

    @DisplayName("팔로우를 추가한다.")
    @Test
    void addFollow() {
        // given
        Follows follows = Follows.builder()
            .fromUserId(1L)
            .toUserId(2L)
            .build();

        // when
        followMapper.addFollow(follows);

        // then
        List<User> followings = followMapper.findAll(1L, GetFollowsType.FOLLOWINGS);
        assertThat(followings).extracting(BasicUser::getUserId).hasSize(1).containsExactly(2L);
    }

    @DisplayName("팔로우를 삭제한다.")
    @Test
    void deleteFollow() {
        // given
        Follows follows = Follows.builder()
            .fromUserId(1L)
            .toUserId(2L)
            .build();

        followMapper.addFollow(follows);

        // when
        followMapper.deleteFollow(follows);

        // then
        List<User> followings = followMapper.findAll(1L, GetFollowsType.FOLLOWINGS);
        assertThat(followings).extracting(BasicUser::getUserId).isEmpty();
    }

    @DisplayName("팔로워들의 아이디 목록을 가져온다.")
    @Test
    void findAllFollowerIds() {
        // given
        Follows follows1 = Follows.builder()
            .fromUserId(2L)
            .toUserId(1L)
            .build();

        Follows follows2 = Follows.builder()
            .fromUserId(3L)
            .toUserId(1L)
            .build();

        Follows follows3 = Follows.builder()
            .fromUserId(4L)
            .toUserId(1L)
            .build();

        // when
        followMapper.addFollow(follows1);
        followMapper.addFollow(follows2);
        followMapper.addFollow(follows3);

        // then
        List<Long> followers = followMapper.findAllFollowerIds(1L);
        assertThat(followers).hasSize(3).containsExactly(2L, 3L, 4L);
    }
}
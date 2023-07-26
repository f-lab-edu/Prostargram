package flab.project.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import flab.project.data.dto.FollowRequestDto;
import flab.project.data.dto.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class FollowMapperTest {

    @Autowired
    FollowMapper followMapper;

    @DisplayName("팔로잉을 가지고 올 수 있다.")
    @Test
    public void findFollowings() {
        //given
        FollowRequestDto followRequestDto1 = new FollowRequestDto(1L, 2L);
        FollowRequestDto followRequestDto2 = new FollowRequestDto(1L, 3L);
        FollowRequestDto followRequestDto3 = new FollowRequestDto(1L, 4L);
        followMapper.postFollow(followRequestDto1);
        followMapper.postFollow(followRequestDto2);
        followMapper.postFollow(followRequestDto3);

        //when
        List<User> followings = followMapper.findAll(GetFollowsType.FOLLOWINGS, 1L);
        //then

        assertThat(followings).hasSize(3)
            .extracting("userId")
            .containsExactlyInAnyOrder(2L, 3L, 4L);
    }

    @DisplayName("팔로워를 가지고 올 수 있다.")
    @Test
    public void findFollowers() {
        //given
        FollowRequestDto followRequestDto1 = new FollowRequestDto(2L, 1L);
        FollowRequestDto followRequestDto2 = new FollowRequestDto(3L, 1L);
        FollowRequestDto followRequestDto3 = new FollowRequestDto(4L, 1L);
        followMapper.postFollow(followRequestDto1);
        followMapper.postFollow(followRequestDto2);
        followMapper.postFollow(followRequestDto3);

        //when
        List<User> followings = followMapper.findAll(GetFollowsType.FOLLOWERS, 1L);
        //then

        assertThat(followings).hasSize(3)
            .extracting("userId")
            .containsExactlyInAnyOrder(2L, 3L, 4L);
    }

    @DisplayName("팔로워/팔로잉을 모두 한번에 가지고 올 수 있다.")
    @Test
    public void findAllFollows() {

        FollowRequestDto followRequestDto1 = new FollowRequestDto(1L, 2L);
        FollowRequestDto followRequestDto2 = new FollowRequestDto(1L, 3L);
        FollowRequestDto followRequestDto3 = new FollowRequestDto(3L, 1L);
        FollowRequestDto followRequestDto4 = new FollowRequestDto(4L, 1L);
        followMapper.postFollow(followRequestDto1);
        followMapper.postFollow(followRequestDto2);
        followMapper.postFollow(followRequestDto3);
        followMapper.postFollow(followRequestDto4);

        //when
        List<User> follows = followMapper.findAll(GetFollowsType.ALL,1L);

        //then
        assertThat(follows).hasSize(3)
            .extracting("userId")
            .containsExactlyInAnyOrder(2L, 3L, 4L);
    }
    //TODO Service에서 테스트하는 내용과 Mapper에서 테스트 하는 내용이 중복되는거 같은데..
    //TODO postFollow는 어떻게 테스트하지? 이미 테스트했다고 생각해도 되는걸까?
}
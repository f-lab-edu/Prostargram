package flab.project.service;


import static org.assertj.core.api.Assertions.assertThat;

import flab.project.data.dto.FollowRequestDto;
import flab.project.data.dto.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.mapper.FollowMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@RequiredArgsConstructor
@Transactional
@SpringBootTest
class FollowServiceTest {

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private FollowService followService;

    @DisplayName("팔로잉을 생성할 수 있다.")
    @Test
    public void createFollowing(){
      //given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L, 2L);
        followService.postFollow(followRequestDto);

        //when
        List<User> follows = followService.getFollows(1L, GetFollowsType.FOLLOWINGS);

        //then
        assertThat(follows.get(0)).extracting("userId").isEqualTo(2L);
    }

    @DisplayName("팔로워를 생성할 수 있다.")
    @Test
    public void createFollowers(){
        //given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L, 2L);
        followService.postFollow(followRequestDto);

        //when
        List<User> follows = followService.getFollows(2L, GetFollowsType.FOLLOWERS);

        //then
        assertThat(follows.get(0)).extracting("userId").isEqualTo(1L);
    }

}
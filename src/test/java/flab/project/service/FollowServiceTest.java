package flab.project.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import flab.project.config.baseresponse.BaseResponse;
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

    @DisplayName("팔로잉 목록을 가져올 수 있다.")
    @Test
    public void createFollowing() {
        //given
        FollowRequestDto followRequestDto1 = new FollowRequestDto(1L, 2L);
        FollowRequestDto followRequestDto2 = new FollowRequestDto(1L, 3L);
        FollowRequestDto followRequestDto3 = new FollowRequestDto(1L, 4L);
        followService.postFollow(followRequestDto1);
        followService.postFollow(followRequestDto2);
        followService.postFollow(followRequestDto3);

        //when
        List<User> follows = followService.getFollows(1L, GetFollowsType.FOLLOWINGS);

        //then
        assertThat(follows).hasSize(3)
            .extracting("userId")
            .containsExactlyInAnyOrder(2L, 3L, 4L);
    }

    @DisplayName("팔로잉을 생성할 수 있다.")
    @Test
    public void createFollowing2() {
        //given
        FollowRequestDto followRequestDto = new FollowRequestDto(1L, 2L);

        //when
        BaseResponse baseResponse = followService.postFollow(followRequestDto);

        //then
        assertThat(baseResponse).extracting("isSuccess").isEqualTo(true);
    }

    @DisplayName("팔로워 목록을 가져올 수 있다")
    @Test
    public void createFollowers() {
        //given
        FollowRequestDto followRequestDto1 = new FollowRequestDto(2L, 1L);
        FollowRequestDto followRequestDto2 = new FollowRequestDto(3L, 1L);
        FollowRequestDto followRequestDto3 = new FollowRequestDto(4L, 1L);
        followService.postFollow(followRequestDto1);
        followService.postFollow(followRequestDto2);
        followService.postFollow(followRequestDto3);

        //when
        List<User> follows = followService.getFollows(1L, GetFollowsType.FOLLOWERS);

        //then
        assertThat(follows).hasSize(3)
            .extracting("userId")
            .containsExactlyInAnyOrder(2L, 3L, 4L);
    }

    @DisplayName("팔로워 생성할 수 있다.")
    @Test
    public void createFollower() {
        //given
        FollowRequestDto followRequestDto = new FollowRequestDto(2L, 1L);

        //when
        BaseResponse baseResponse = followService.postFollow(followRequestDto);

        //then
        assertThat(baseResponse).extracting("isSuccess").isEqualTo(true);
    }

}
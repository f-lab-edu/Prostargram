package flab.project.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import org.springframework.dao.DataIntegrityViolationException;
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
        List<User> follows = followService.getFollows(1L, GetFollowsType.FOLLOWINGS).getResult();

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
        List<User> follows = followService.getFollows(1L, GetFollowsType.FOLLOWERS).getResult();

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

    @DisplayName("팔로워/팔로잉 목록을 가져온 결과가 빈 배열일 경우 result에 빈배열이 들어간 채로 반환된다.")
    @Test
    public void whenFollowMapperReturnEmptyServiceReturnEmptyList() {
        //when
        List<User> follows = followService.getFollows(1L, GetFollowsType.FOLLOWERS).getResult();

        System.out.println("follows = " + follows);
        //then
        assertThat(follows).isEmpty();
    }

    @DisplayName("이미 팔로우가 되어있는 상태에서 팔로우 요청이 올 경우 DuplicateKeyException이 발생한다.")
    @Test
    public void ifFollowRequestWhenFollowingThrowDuplicateKeyException() {
        //given
        FollowRequestDto followRequestDto1 = new FollowRequestDto(2L, 1L);
        FollowRequestDto followRequestDto2 = new FollowRequestDto(2L, 1L);

        //when then
        followService.postFollow(followRequestDto1);
        assertThatThrownBy(() -> followService.postFollow(followRequestDto2))
            .isInstanceOf(RuntimeException.class);


    }

    @DisplayName("존재하지 않는 유저에게 팔로우 요청을 보낼 경우 DataIntegrityViolationException이 발생한다.")
    @Test
    public void ifFollowNonExistUserThrowDataIntegrityViolationException() {
        //given
        long nonExistUserId1=9999L;
        long nonExistUserId2=10000L;

        FollowRequestDto followRequestDto = new FollowRequestDto(nonExistUserId1, nonExistUserId2);

        //when then
        assertThatThrownBy(() -> followService.postFollow(followRequestDto))
            .isInstanceOf(DataIntegrityViolationException.class);


    }
}
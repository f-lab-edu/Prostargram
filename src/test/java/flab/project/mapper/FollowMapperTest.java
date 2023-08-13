//package flab.project.mapper;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import flab.project.data.dto.model.Follows;
//import flab.project.data.dto.model.User;
//import flab.project.data.enums.requestparam.GetFollowsType;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//@ActiveProfiles("test")
//@Transactional
//@SpringBootTest
//class FollowMapperTest {
//
//    @Autowired
//    FollowMapper followMapper;
//
//    @DisplayName("팔로잉을 가지고 올 수 있다.")
//    @Test
//    public void findFollowings() {
//        //given
//        Follows follows1 = new Follows(1L, 2L);
//        Follows follows2 = new Follows(1L, 3L);
//        Follows follows3 = new Follows(1L, 4L);
//        followMapper.addFollow(follows1);
//        followMapper.addFollow(follows2);
//        followMapper.addFollow(follows3);
//
//        //when
//        List<User> followings = followMapper.findAll(GetFollowsType.FOLLOWINGS, 1L);
//        //then
//
//        assertThat(followings).hasSize(3)
//            .extracting("userId")
//            .containsExactlyInAnyOrder(2L, 3L, 4L);
//    }
//
//    @DisplayName("팔로워를 가지고 올 수 있다.")
//    @Test
//    public void findFollowers() {
//        //given
//        Follows follows1 = new Follows(2L, 1L);
//        Follows follows2 = new Follows(3L, 1L);
//        Follows follows3 = new Follows(4L, 1L);
//        followMapper.addFollow(follows1);
//        followMapper.addFollow(follows2);
//        followMapper.addFollow(follows3);
//
//        //when
//        List<User> followings = followMapper.findAll(GetFollowsType.FOLLOWERS, 1L);
//        //then
//
//        assertThat(followings).hasSize(3)
//            .extracting("userId")
//            .containsExactlyInAnyOrder(2L, 3L, 4L);
//    }
//
//    @DisplayName("팔로워/팔로잉을 모두 한번에 가지고 올 수 있다.")
//    @Test
//    public void findAllFollows() {
//
//        Follows follows1 = new Follows(1L, 2L);
//        Follows follows2 = new Follows(1L, 3L);
//        Follows follows3 = new Follows(3L, 1L);
//        Follows follows4 = new Follows(4L, 1L);
//        followMapper.addFollow(follows1);
//        followMapper.addFollow(follows2);
//        followMapper.addFollow(follows3);
//        followMapper.addFollow(follows4);
//
//        //when
//        List<User> follows = followMapper.findAll(GetFollowsType.ALL,1L);
//
//        //then
//        assertThat(follows).hasSize(3)
//            .extracting("userId")
//            .containsExactlyInAnyOrder(2L, 3L, 4L);
//    }
//    //TODO Service에서 테스트하는 내용과 Mapper에서 테스트 하는 내용이 중복되는거 같은데..
//    //TODO postFollow는 어떻게 테스트하지? 이미 테스트했다고 생각해도 되는걸까?
//}
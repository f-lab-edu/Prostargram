package flab.project.domain.user.mapper;

import flab.project.domain.user.model.BasicUser;
import flab.project.domain.user.model.Profile;
import flab.project.domain.user.model.ProfilePage;
import flab.project.domain.user.model.UpdateProfileRequestDto;

import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    ProfilePage getProfilePageInfo(@Param("myUserId") long myUserId, @Param("targetUserId") long targetUserId);
    Profile getProfileUpdatePageInfo(@Param("userId") long userId);

    BasicUser getBasicUser(@Param("userId") long userId);

    int updateProfile(@Param("userId") long userId, @Param("updateProfileRequestDto") UpdateProfileRequestDto updateProfileRequestDto);

    int updateProfileImage(@Param("userId") long userId, @Param("profileImgUrl") String profileImgUrl);

    Set<Profile> findWhereUserIdIn(@Param("userIds") Set<Long> userIds);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    void incrementPostCount(long userId);
}
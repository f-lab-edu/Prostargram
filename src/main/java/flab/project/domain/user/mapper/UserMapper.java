package flab.project.domain.user.mapper;

import flab.project.domain.user.model.BasicUser;
import flab.project.domain.user.model.Profile;
import flab.project.domain.user.enums.GetProfileRequestType;
import flab.project.domain.user.model.UpdateProfileRequestDto;

import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    Profile getProfileInfo(@Param("userId") long userId, @Param("getProfileRequestType") GetProfileRequestType getProfileRequestType);

    BasicUser getBasicUser(@Param("userId") long userId);

    int updateProfile(@Param("userId") long userId, @Param("updateProfileRequestDto") UpdateProfileRequestDto updateProfileRequestDto);

    int updateProfileImage(@Param("userId") long userId, @Param("profileImgUrl") String profileImgUrl);

    Set<Profile> findWhereUserIdIn(@Param("userIds") Set<Long> userIds);

    boolean existsByEmail(String email);
}
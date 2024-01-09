package flab.project.mapper;

import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import flab.project.data.dto.UpdateProfileRequestDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    Profile getProfileInfo(@Param("userId") long userId, @Param("getProfileRequestType") GetProfileRequestType getProfileRequestType);

    BasicUser getBasicUser(@Param("userId") long userId);

    int updateProfile(@Param("userId") long userId, @Param("updateProfileRequestDto") UpdateProfileRequestDto updateProfileRequestDto);

    int updateProfileImage(@Param("userId") long userId, @Param("profileImgUrl") String profileImgUrl);

    List<Profile> findByUserIdIn(@Param("userIds") List<Long> userIds);
}
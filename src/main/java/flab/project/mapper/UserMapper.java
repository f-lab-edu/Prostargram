package flab.project.mapper;

import flab.project.data.dto.BasicPostWithUser;
import flab.project.data.dto.UpdateProfileRequestDto;
import flab.project.data.dto.model.BasicUser;
import flab.project.data.dto.model.Profile;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    Profile getProfileInfo(@Param("userId") long userId, @Param("getProfileRequestType") GetProfileRequestType getProfileRequestType);

    BasicUser getBasicUser(@Param("userId") long userId);

    int updateProfile(@Param("userId") long userId, @Param("updateProfileRequestDto") UpdateProfileRequestDto updateProfileRequestDto);

    int updateProfileImage(@Param("userId") long userId, @Param("profileImgUrl") String profileImgUrl);

    List<BasicPostWithUser> getProfileFeed(@Param("userId") long userId, @Param("lastProfilePostId") Long lastProfilePostId, @Param("limit") long limit);
}
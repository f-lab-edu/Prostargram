package flab.project.mapper;

import flab.project.data.dto.Profile;
import flab.project.data.dto.User;
import flab.project.data.enums.requestparam.GetFollowsType;
import flab.project.data.enums.requestparam.GetProfileRequestType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll(@Param("requestType") GetFollowsType requestType, @Param("userId") long userId);

    Profile getProfileInfo(@Param("userId") long userId, @Param("getProfileRequestType") GetProfileRequestType getProfileRequestType);
}

package flab.project.mapper;

import flab.project.data.dto.User;
import flab.project.data.entity.Follows;
import flab.project.data.enums.requestparam.GetFollowsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll(@Param("requestType") GetFollowsType requestType, @Param("loginedId") long loginedId);
}

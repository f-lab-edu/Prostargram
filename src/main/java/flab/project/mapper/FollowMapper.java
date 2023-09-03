package flab.project.mapper;

import flab.project.data.dto.model.Follows;
import flab.project.data.dto.model.User;
import flab.project.data.enums.requestparam.GetFollowsType;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowMapper {
    List<User> findAll(@Param("userId") long userId, @Param("requestType") GetFollowsType requestType);

    int addFollow(@Param("followRequestDto") Follows follows);

    void deleteFollow(@Param("followRequestDto") Follows follows);
}

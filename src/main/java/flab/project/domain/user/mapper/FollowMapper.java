package flab.project.domain.user.mapper;

import flab.project.domain.user.model.Follows;
import flab.project.domain.user.model.User;
import flab.project.domain.user.enums.GetFollowsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowMapper {
    List<User> findAll(@Param("userId") long userId, @Param("requestType") GetFollowsType requestType);

    int addFollow(@Param("followRequestDto") Follows follows);

    void deleteFollow(@Param("followRequestDto") Follows follows);
}
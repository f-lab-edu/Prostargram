package flab.project.domain.user.mapper;

import flab.project.domain.user.model.UserForAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthenticationMapper {

    UserForAuth getUser(@Param("email") String email);
}
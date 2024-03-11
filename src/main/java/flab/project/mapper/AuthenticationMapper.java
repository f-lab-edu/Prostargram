package flab.project.mapper;

import flab.project.data.dto.UserForAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthenticationMapper {

    UserForAuth getUser(@Param("email") String email);
}
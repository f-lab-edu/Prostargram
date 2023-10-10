package flab.project.mapper;

import flab.project.data.FormLoginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthenticationMapper {

    FormLoginDto getUser(@Param("email") String email);
}
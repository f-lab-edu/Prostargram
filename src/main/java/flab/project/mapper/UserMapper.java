package flab.project.mapper;

import flab.project.data.dto.UpdateProfileRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    int updateProfile(@Param("userId") long userId, @Param("updateProfileRequestDto") UpdateProfileRequestDto updateProfileRequestDto);
}

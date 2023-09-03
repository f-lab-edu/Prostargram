package flab.project.mapper;

import flab.project.data.enums.PublicScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper {

    int updateUserPublicScope(@Param("userId") long userId, @Param("publicScope") PublicScope publicScope);
}

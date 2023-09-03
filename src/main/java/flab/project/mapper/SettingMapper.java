package flab.project.mapper;

import flab.project.data.dto.Settings;
import flab.project.data.enums.PublicScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper {

    Settings getPersonalSettingsByUserId(@Param("userId") long userId);

    int updateUserPublicScope(@Param("userId") long userId, @Param("publicScope") PublicScope publicScope);
}

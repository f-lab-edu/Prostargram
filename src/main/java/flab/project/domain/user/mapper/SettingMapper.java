package flab.project.domain.user.mapper;

import flab.project.domain.user.model.Settings;
import flab.project.domain.user.enums.PublicScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper {

    Settings getPersonalSettingsByUserId(@Param("userId") long userId);

    int updateUserPublicScope(@Param("userId") long userId, @Param("publicScope") PublicScope publicScope);
}
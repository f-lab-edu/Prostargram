package flab.project.mapper;

import flab.project.data.dto.Settings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper {

    Settings getPersonalSettingsByUserId(@Param("userId") long userId);
}

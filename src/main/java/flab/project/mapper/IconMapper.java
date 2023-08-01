package flab.project.mapper;

import flab.project.data.dto.model.Icon;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IconMapper {
    List<Icon> findAll();
}
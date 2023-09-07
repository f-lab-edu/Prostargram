package flab.project.mapper;

import flab.project.data.enums.ExtensionType;
import java.util.HashSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FileExtensionMapper {

    HashSet<String> findAllByType(@Param("type") ExtensionType type);
}

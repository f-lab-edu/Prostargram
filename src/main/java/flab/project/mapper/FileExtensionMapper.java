package flab.project.mapper;

import flab.project.data.enums.ExtensionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;

@Mapper
public interface FileExtensionMapper {

    HashSet<String> findAllByType(@Param("type") ExtensionType type);
}
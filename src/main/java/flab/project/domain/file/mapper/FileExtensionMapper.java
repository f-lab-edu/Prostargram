package flab.project.domain.file.mapper;

import flab.project.domain.file.enums.ExtensionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashSet;

@Mapper
public interface FileExtensionMapper {

    HashSet<String> findAllByType(@Param("type") ExtensionType type);
}
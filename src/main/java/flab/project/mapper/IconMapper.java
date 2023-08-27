package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IconMapper {

    Long findByDomain(@Param("domain") String domain);
}

package flab.project.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DuplicationMapper {
    Integer countUserName(@Param("userName") String userName);
}

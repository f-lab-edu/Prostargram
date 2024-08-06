package flab.project.domain.user.mapper;

import flab.project.domain.user.model.Icon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IconMapper {

    Icon findByDomain(@Param("domain") String domain);

    void save(@Param("domain") String domain, @Param("iconUrl") String iconUrl);
}
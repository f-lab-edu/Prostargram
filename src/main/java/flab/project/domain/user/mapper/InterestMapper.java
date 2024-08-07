package flab.project.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InterestMapper {

    int getNumberOfExistingInterests(@Param("userId") long userId);

    void save(@Param("userId") long userId, @Param("hashTagId") long hashTagId, @Param("name") String name);

    int delete(@Param("userId") long userId, @Param("hashTagId") long hashTagId, @Param("name") String name);
}
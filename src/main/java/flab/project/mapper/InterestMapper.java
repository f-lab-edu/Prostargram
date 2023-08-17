package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InterestMapper {

    int getNumberOfExistingInterests(@Param("userId") long userId);

    void save(@Param("userId") long userId, @Param("hashtagId") long hashtagId);

    int delete(@Param("userId") long userId, @Param("hashtagId") Long hashtagId);

}

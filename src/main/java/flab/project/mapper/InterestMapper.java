package flab.project.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InterestMapper {

    List<String> findAllByUserId(@Param("userId") long userId);

    void insertAllIn(@Param("userId") long userId, @Param("interests") List<String> interests);

    void deleteAllIn(@Param("userId") long userId, @Param("interests") List<String> interests);
}

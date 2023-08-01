package flab.project.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HashtagMapper {
    List<String> retrieveHashtagsIn(@Param("interests") List<String> interests);

    void insertAll(@Param("hashtags") List<String> hashtags);
}

package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostOptionMapper {
    int saveAll(@Param("postId") long postId, @Param("optionContents") List<String> optionContents);
}

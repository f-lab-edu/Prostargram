package flab.project.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostOptionMapper {

    int saveAll(@Param("postId") long postId, @Param("optionContents") Set<String> optionContents);
}
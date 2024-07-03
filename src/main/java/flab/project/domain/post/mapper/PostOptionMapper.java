package flab.project.domain.post.mapper;

import java.util.Set;

import flab.project.domain.user.model.Option;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostOptionMapper {

    void saveAll(@Param("postId") long postId, @Param("optionContents") Set<String> optionContents);

    Set<Option> findAll(@Param("postId") long postId);
}
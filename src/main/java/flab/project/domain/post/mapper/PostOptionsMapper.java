package flab.project.domain.post.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface PostOptionsMapper {

    Set<Long> findValidOptionIds(@Param("postId") long postId);
}

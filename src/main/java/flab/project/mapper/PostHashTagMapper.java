package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface PostHashTagMapper {

    void saveAll(@Param("postId") long postId, @Param("hashTagIds") Set<Long> hashTagIds);
}
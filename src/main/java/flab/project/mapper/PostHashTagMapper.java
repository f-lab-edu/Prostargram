package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostHashTagMapper {

    int saveAll(@Param("postId") long postId, @Param("hashTagIds") List<Long> hashTagIds);
}

package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostHashTagMapper {

    public int saveAll(List<Long> hashTagIds);
}

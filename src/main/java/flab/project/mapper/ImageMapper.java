package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {

    int saveAll(List<String> contentImgUrls);
}

package flab.project.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface BadWordMapper {

    public Set<String> findAll();
}
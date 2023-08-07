package flab.project.mapper;

import java.util.Set;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BadwordMapper {

    public Set<String> getAll();
}

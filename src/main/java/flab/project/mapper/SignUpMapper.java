package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignUpMapper {
    void addUser(String email, String password, String userName);
}

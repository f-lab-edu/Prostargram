package flab.project.mapper;

import flab.project.data.enums.LoginType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignUpMapper {
    void addUser(String email, String userName, String password, LoginType loginType);
}

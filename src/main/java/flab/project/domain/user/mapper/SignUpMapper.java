package flab.project.domain.user.mapper;

import flab.project.domain.user.enums.LoginType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignUpMapper {
    void addUser(String email, String userName, String password, LoginType loginType);
}

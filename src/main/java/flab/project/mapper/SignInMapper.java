package flab.project.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignInMapper {
    void addUserInformation(String email, String password, String userName);
}

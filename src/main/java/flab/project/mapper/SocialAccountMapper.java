package flab.project.mapper;

import flab.project.data.dto.model.SocialAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SocialAccountMapper {

    int getNumberOfExistingSocialAccounts(@Param("userId") long userId);

    void save(@Param("socialAccount") SocialAccount socialAccount);

    void remove(@Param("socialAccount") SocialAccount socialAccount);
}
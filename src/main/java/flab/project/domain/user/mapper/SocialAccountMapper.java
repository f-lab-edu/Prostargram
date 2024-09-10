package flab.project.domain.user.mapper;

import flab.project.domain.user.model.SocialAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SocialAccountMapper {

    int getNumberOfExistingSocialAccounts(@Param("userId") long userId);

    void save(@Param("socialAccount") SocialAccount socialAccount);

    int remove(@Param("socialAccount") SocialAccount socialAccount);
}
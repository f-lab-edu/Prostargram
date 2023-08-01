package flab.project.mapper;

import flab.project.data.dto.model.SocialAccounts;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HyperLinkMapper {

    List<String> findAllByUserId(@Param("userId") long userId);

    int insertAll(@Param("toAddSocialAccounts") List<SocialAccounts> toAddSocialAccounts,
        @Param("userId") long userId);

    void deleteAll(@Param("toDeleteSocialAccounts") List<SocialAccounts> toDeleteSocialAccounts,
        @Param("userId") long userId);
}
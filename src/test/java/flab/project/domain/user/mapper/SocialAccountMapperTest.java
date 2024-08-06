package flab.project.domain.user.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import flab.project.domain.user.model.SocialAccount;
import flab.project.domain.user.model.UpdateSocialAccountRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class SocialAccountMapperTest {

    @Autowired
    SocialAccountMapper socialAccountMapper;

    @DisplayName("소셜 계정 저장하기")
    @Test
    void save() {
        // given
        long userId = 1;
        String socialAccountUrl = "https://social-account.url";
        SocialAccount socialAccount = createSocialAccount(userId, socialAccountUrl);

        socialAccountMapper.save(socialAccount);

        // when
        // then
        int numberOfSocialAccounts = socialAccountMapper.getNumberOfExistingSocialAccounts(userId);
        assertThat(numberOfSocialAccounts).isEqualTo(1);
    }

    @DisplayName("소셜 계정 삭제하기")
    @Test
    void remove() {
        // given
        long userId = 1;
        String socialAccountUrl1 = "https://social-account.url";
        String socialAccountUrl2 = "https://social-account2.url";
        SocialAccount socialAccount = createSocialAccount(userId, socialAccountUrl1);
        SocialAccount socialAccount2 = createSocialAccount(userId, socialAccountUrl2);

        socialAccountMapper.save(socialAccount);
        socialAccountMapper.save(socialAccount2);

        // when
        socialAccountMapper.remove(socialAccount2);

        // then
        int numberOfSocialAccounts = socialAccountMapper.getNumberOfExistingSocialAccounts(userId);
        assertThat(numberOfSocialAccounts).isEqualTo(1);
    }

    private SocialAccount createSocialAccount(long userId, String socialAccountUrl) {

        UpdateSocialAccountRequestDto requestDto = new UpdateSocialAccountRequestDto(
            userId,
            socialAccountUrl
        );
        SocialAccount socialAccount = new SocialAccount(requestDto);
        socialAccount.setIconId(1L);
        return socialAccount;
    }
}
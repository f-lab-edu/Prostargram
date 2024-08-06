package flab.project.domain.user.mapper;

import static org.assertj.core.api.Assertions.*;

import flab.project.domain.user.model.Icon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:tableInit.sql")
@ActiveProfiles("test")
@MybatisTest
class IconMapperTest {

    @Autowired
    IconMapper iconMapper;

    @DisplayName("도메인을 통해 Icon을 조회한다.")
    @Test
    void findByDomain() {
        // given
        String domain = "domain";
        String iconUrl = "https://icon.url";

        iconMapper.save(domain, iconUrl);

        // when
        Icon icon = iconMapper.findByDomain(domain);

        // then
        assertThat(icon.getIconUrl()).isEqualTo(iconUrl);
    }
}
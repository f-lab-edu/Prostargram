package flab.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.List;

public class IntegrationTestExecutionListener extends AbstractTestExecutionListener {
    @Override
    public void afterTestMethod(final TestContext testContext) {
        final JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
        final List<String> truncateQueries = getTruncateQueries(jdbcTemplate);
        truncateTables(jdbcTemplate, truncateQueries);

        //todo 회원가입 기능이 없어 임시로 넣어놓은 기능.
        initializeUser(jdbcTemplate);
    }

    private void initializeUser(JdbcTemplate jdbcTemplate) {
        execute(jdbcTemplate,"INSERT INTO prostargram.users (user_id, organization_id, user_name, email, password, self_introduction, profile_img_url, post_count, follower_count, following_count, `type(관리자, 일반유저 여부)`, login_type) VALUES (1, 1, 'df', 'dsf', 'sdf', 'sdf', 'sdf', 0, 0, 0, 'NORMAL_USER', 'GITHUB');");
        execute(jdbcTemplate,"INSERT INTO prostargram.users (user_id, organization_id, user_name, email, password, self_introduction, profile_img_url, post_count, follower_count, following_count, `type(관리자, 일반유저 여부)`, login_type) VALUES (2, 1, 'df', 'dsf', 'sdf', 'sdf', 'sdf', 0, 0, 0, 'NORMAL_USER', 'GITHUB');");
    }

    private List<String> getTruncateQueries(final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList("select Concat('TRUNCATE TABLE `', TABLE_NAME, '`;') from information_schema.TABLES WHERE TABLE_SCHEMA='prostargram'", String.class);
    }

    private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
        return testContext.getApplicationContext().getBean(JdbcTemplate.class);
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> truncateQueries) {
        execute(jdbcTemplate, "SET FOREIGN_KEY_CHECKS = 0");
        truncateQueries.forEach(v -> execute(jdbcTemplate, v));
        execute(jdbcTemplate, "SET FOREIGN_KEY_CHECKS = 1");
    }

    private void execute(final JdbcTemplate jdbcTemplate, final String query) {
        jdbcTemplate.execute(query);
    }
}

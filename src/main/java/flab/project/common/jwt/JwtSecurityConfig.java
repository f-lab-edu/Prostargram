package flab.project.common.jwt;

import flab.project.common.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    @Override
    public void configure(final HttpSecurity builder) {
        final JwtFilter filter = new JwtFilter(tokenProvider, redisRepository);

        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
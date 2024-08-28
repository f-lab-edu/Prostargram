package flab.project.domain.user.service;

import flab.project.common.RedisRepository;
import flab.project.common.jwt.TokenProvider;
import flab.project.domain.user.model.FormLoginRequest;
import flab.project.domain.user.model.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final long REISSUE_STANDARD_TIME_7_DAYS = 1_000 * 60 * 60 * 24 * 7L;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    public TokenDto formLogin(FormLoginRequest formLoginRequest) {
        final Authentication authentication = saveSecurityContext(formLoginRequest);

        return createLoginResponse(authentication);
    }

    private Authentication saveSecurityContext(FormLoginRequest formLoginRequest) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(formLoginRequest.getEmail(), formLoginRequest.getPassword());
        final Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

    private TokenDto createLoginResponse(Authentication authentication) {
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto reissue(Long userId, Authentication authentication) {
        String currentRefreshToken = authentication.getCredentials().toString();

        String createdAccessToken = tokenProvider.createAccessToken(authentication);
        String createdRefreshToken = createRefreshToken(authentication, currentRefreshToken, userId);

        return TokenDto.builder()
                .accessToken(createdAccessToken)
                .refreshToken(createdRefreshToken)
                .build();
    }

    private String createRefreshToken(Authentication authentication, String currentRefreshToken, long userId) {
        redisRepository.saveBlackListToken(currentRefreshToken, userId, new Date());
        return tokenProvider.createRefreshToken(authentication);
    }
}
package flab.project.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.project.common.RedisRepository;
import flab.project.config.exception.FailLogoutRequestException;
import flab.project.domain.user.model.TokenDto;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class AddBlackListLogoutHandler implements LogoutHandler {

    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Date now = new Date();
        long userId = extractUserId(request);

        try {
            TokenDto tokenDto = extractHttpBody(request);
            String accessToken = tokenDto.getAccessToken();
            String refreshToken = tokenDto.getRefreshToken();

            redisRepository.saveBlackListToken(accessToken, userId, now);
            redisRepository.saveBlackListToken(refreshToken, userId, now);
        } catch (Exception e) {
            throw new FailLogoutRequestException();
        }
    }

    private TokenDto extractHttpBody(HttpServletRequest request) throws IOException {
        ServletInputStream httpBody = request.getInputStream();
        return objectMapper.readValue(httpBody, TokenDto.class);
    }

    private long extractUserId(HttpServletRequest request) {
        String jwt = tokenProvider.resolveToken(request);
        return tokenProvider.extractUserId(jwt);
    }
}
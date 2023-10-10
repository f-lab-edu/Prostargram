package flab.project.common.jwt;

import flab.project.common.RedisRepository;
import flab.project.config.exception.BlackListTokenException;
import flab.project.data.enums.NotMatchTokenTypeAndPurpose;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String REISSUE_PATH = "/reissue";

    private final TokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = tokenProvider.resolveToken(httpServletRequest);

        if (validateToken(request, response, chain, jwt)) {
            return;
        }
        validateTokenUsage(request, jwt);
        validateBlacklist(jwt);
        
        Authentication authentication = tokenProvider.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private void validateBlacklist(String jwt) {
        if(redisRepository.isBlackListToken(jwt)){
            throw new BlackListTokenException();
        }
    }

    private void validateTokenUsage(ServletRequest request, String jwt) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        boolean isReissuePath = requestURI.equals(REISSUE_PATH);
        boolean isRefreshToken = tokenProvider.isRefreshToken(jwt);

        // TODO XOR연산... 쓰는 사람을 본 적이 없는데.. 괜찮나..?
        if (isReissuePath ^ isRefreshToken) {
            throw new NotMatchTokenTypeAndPurpose();
        }
    }

    private boolean validateToken(ServletRequest request, ServletResponse response, FilterChain chain, String jwt) throws IOException, ServletException {
        if (!StringUtils.hasText(jwt) || tokenProvider.isInvalidToken(jwt)) {
            chain.doFilter(request, response);
            return true;
        }
        return false;
    }
}
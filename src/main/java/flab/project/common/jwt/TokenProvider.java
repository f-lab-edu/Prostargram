package flab.project.common.jwt;

import flab.project.data.enums.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

@Component
public class TokenProvider implements InitializingBean {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORITIES_KEY = "authority";
    private static final String TOKEN_TYPE_KEY = "tokenType";

    private static final long ACCESS_TOKEN_VALIDITY_30_MINUTES = 1_000 * 60 * 30L;
    private static final long REFRESH_TOKEN_VALIDITY_2_WEEKS = 1_000 * 60 * 60 * 24 * 14L;

    private final String secret;
    private Key SIGNING_KEY;

    public TokenProvider(
            @Value("${jwt.secret.key}") String secret) {
        this.secret = secret;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.SIGNING_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
        }

        return null;
    }

    public String createAccessToken(Authentication authentication) {
        HashMap<String, String> extraClaims = createExtraClaims(authentication, TokenType.ACCESS_TOKEN);

        Date validity = new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_30_MINUTES);

        return createToken(authentication, extraClaims, validity);
    }

    public String createRefreshToken(Authentication authentication) {
        HashMap<String, String> extraClaims = createExtraClaims(authentication, TokenType.REFRESH_TOKEN);

        Date validity = new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY_2_WEEKS);

        return createToken(authentication, extraClaims, validity);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractAllClaims(token);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public long extractUserId(String jwtToken) {
        final Claims claims = extractAllClaims(jwtToken);

        return Long.parseLong(claims.getSubject());
    }

    public Date extractExpiredAt(String jwtToken) {
        final Claims claims = extractAllClaims(jwtToken);

        return claims.getExpiration();
    }

    public boolean isRefreshToken(String jwt) {
        TokenType tokenType = extractTokenType(jwt);

        return tokenType.equals(TokenType.REFRESH_TOKEN);
    }

    public boolean isInvalidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token);
            return false;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // todo logger 적용 후, 수정 예정.
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return true;
    }

    private HashMap<String, String> createExtraClaims(Authentication authentication, TokenType tokenType) {
        HashMap<String, String> extraClaims = new HashMap<>();
        extraClaims.put(AUTHORITIES_KEY, getAuthorities(authentication));
        extraClaims.put(TOKEN_TYPE_KEY, tokenType.name());

        return extraClaims;
    }

    private String createToken(Authentication authentication, HashMap<String, String> extraClaims, Date validity) {
        return Jwts.builder()
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
                .setClaims(extraClaims)
                .setSubject(authentication.getName())
                .setExpiration(validity)
                .compact();
    }

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private TokenType extractTokenType(String jwtToken) {
        final Claims claims = extractAllClaims(jwtToken);

        String tokenType = claims.get(TOKEN_TYPE_KEY, String.class);

        return TokenType.valueOf(tokenType);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
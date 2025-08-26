package kr.gravy.gravy.auth.utils;

import kr.gravy.gravy.auth.jwt.JWTUtil;
import kr.gravy.gravy.configuration.properties.ServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.springframework.boot.web.server.Cookie.SameSite;

@Component
@RequiredArgsConstructor
public class CookieUtil {

    private final JWTUtil jwtUtil;
    private final ServerProperties serverProperties;

    public static final String ACCESS_COOKIE = "access_token";
    public static final String REFRESH_COOKIE = "refresh_token";

    private static final String REFRESH_TOKEN_REISSUE_PATH = "/api/v1/auth/tokens/reissue";
    private static final String ACCESS_TOKEN_USABLE_PATH = "/api/v1";

    public ResponseCookie createAccessTokenCookie(String token) {
        long maxAge = secondsUntilExpiry(token);

        return ResponseCookie.from(ACCESS_COOKIE, token)
                .httpOnly(true)
                .secure(serverProperties.ssl().enabled())
                .sameSite(SameSite.LAX.attributeValue())
                .path(ACCESS_TOKEN_USABLE_PATH)
                .maxAge(maxAge)
                .build();
    }

    public ResponseCookie createRefreshTokenCookie(String token) {
        long maxAge = secondsUntilExpiry(token);

        return ResponseCookie.from(REFRESH_COOKIE, token)
                .httpOnly(true)
                .secure(serverProperties.ssl().enabled())
                .sameSite(SameSite.STRICT.attributeValue())
                .path(REFRESH_TOKEN_REISSUE_PATH)
                .maxAge(maxAge)
                .build();
    }

    private long secondsUntilExpiry(String jwt) {
        Date exp = jwtUtil.getExpiration(jwt);
        long seconds = Duration.between(Instant.now(), exp.toInstant()).getSeconds();
        return Math.max(0, seconds);
    }
}

package kr.gravy.gravy.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtil {

    private final Key key;

    private final Duration ACCESS_TOKEN_EXPIRATION = Duration.ofMinutes(30);
    private final Duration REFRESH_TOKEN_EXPIRATION = Duration.ofDays(7);

    public JWTUtil(@Value("${jwt.secret}") String secretB64) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretB64));
    }

    public String createAccessToken(UUID userPublicId) {
        return createToken(userPublicId, ACCESS_TOKEN_EXPIRATION);
    }

    public String createRefreshToken(UUID userPublicId) {
        return createToken(userPublicId, REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(UUID userPublicId, Duration duration) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userPublicId.toString())
                .issuer("Gravy")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(duration)))
                .signWith(key)
                .compact();
    }

    public Date getExpiration(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public UUID validateAndGetSubjectAsUUID(String token) {
        String sub = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return UUID.fromString(sub);
    }
}

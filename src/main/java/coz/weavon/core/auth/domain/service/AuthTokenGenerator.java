package coz.weavon.core.auth.domain.service;

import coz.weavon.core.auth.domain.model.AuthToken;
import coz.weavon.core.auth.domain.model.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenGenerator {

    @Value("${auth.jwt.secret}")
    private String secret;

    @Value("${auth.jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Value("${auth.jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    public static SecretKey secretKey;

    public static long accessTokenExpirationMillis;

    public static long refreshTokenExpirationMillis;

    @PostConstruct
    public void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @PostConstruct
    public void setExpirationMillis() {
        accessTokenExpirationMillis = (long) accessTokenExpirationMinutes * 60 * 1000;
        refreshTokenExpirationMillis = (long) refreshTokenExpirationMinutes * 60 * 1000;
    }

    public static AuthToken generateAuthToken(AuthUser authUser) {
        String accessToken = generateAccessToken(authUser);
        String refreshToken = generateRefreshToken(authUser);

        return AuthToken.of(accessToken, refreshToken);
    }

    public static String generateAccessToken(AuthUser authUser) {
        Map<String, Object> claims = generateClaims(authUser, "ACCESS_TOKEN");

        Date generateDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + accessTokenExpirationMillis);

        return Jwts.builder()
                .claims(claims)
                .subject(authUser.getUsername())
                .issuedAt(generateDate)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public static String generateRefreshToken(AuthUser authUser) {
        Map<String, Object> claims = generateClaims(authUser, "REFRESH_TOKEN");

        Date generateDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + refreshTokenExpirationMillis);

        return Jwts.builder()
                .claims(claims)
                .subject(authUser.getUsername())
                .issuedAt(generateDate)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public static Map<String, Object> generateClaims(AuthUser authUser, String tokenType) {
        return Map.of(
                "userId", authUser.getUserId(),
                "username", authUser.getUsername(),
                "password", authUser.getPassword(),
                "role", authUser.getRole(),
                "tokenType", tokenType);
    }
}

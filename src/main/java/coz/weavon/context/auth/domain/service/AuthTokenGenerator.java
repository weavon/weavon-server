package coz.weavon.context.auth.domain.service;

import coz.weavon.context.auth.domain.model.AuthToken;
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

    @Value("${auth.jwt.expiration-minutes}")
    private int expirationMinutes;

    public static SecretKey secretKey;

    public static long expirationMillis;

    @PostConstruct
    public void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @PostConstruct
    public void setExpirationMillis() {
        expirationMillis = (long) expirationMinutes * 60 * 1000;
    }

    public static AuthToken generateAuthToken(String username, String role) {
        return generateAuthToken(Map.of("role", role), username);
    }

    private static AuthToken generateAuthToken(Map<String, Object> claims, String username) {
        Date generateDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + expirationMillis);

        String tokenValue = Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(generateDate)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();

        return AuthToken.of(tokenValue);
    }
}

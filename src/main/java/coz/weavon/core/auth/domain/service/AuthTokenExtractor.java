package coz.weavon.core.auth.domain.service;

import coz.weavon.core.auth.domain.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthTokenExtractor {

    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(AuthTokenGenerator.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    public static AuthUser extractAuthUser(String token) {
        return AuthUser.builder()
                .userId(extractUserId(token))
                .username(extractUsername(token))
                .password(extractPassword(token))
                .role(extractRole(token))
                .build();
    }

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public static String extractPassword(String token) {
        return extractClaim(token, claims -> claims.get("password", String.class));
    }

    public static String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

package coz.weavon.core.auth.domain.service;

import coz.weavon.core.auth.domain.model.AuthToken;
import coz.weavon.core.auth.domain.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthTokenExtractor {

    public static <T> T extractClaim(AuthToken authToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractClaims(authToken);
        return claimResolver.apply(claims);
    }

    public static Claims extractClaims(AuthToken authToken) {
        return Jwts.parser()
                .verifyWith(AuthTokenGenerator.secretKey)
                .build()
                .parseSignedClaims(authToken.getValue())
                .getPayload();
    }

    public static AuthUser extractAuthUser(AuthToken authToken) {
        return AuthUser.builder()
                .userId(extractUserId(authToken))
                .username(extractUsername(authToken))
                .password(extractPassword(authToken))
                .role(extractRole(authToken))
                .build();
    }

    public static String extractUsername(AuthToken authToken) {
        return extractClaim(authToken, Claims::getSubject);
    }

    public static Long extractUserId(AuthToken authToken) {
        return extractClaim(authToken, claims -> claims.get("userId", Long.class));
    }

    public static String extractPassword(AuthToken authToken) {
        return extractClaim(authToken, claims -> claims.get("password", String.class));
    }

    public static String extractRole(AuthToken authToken) {
        return extractClaim(authToken, claims -> claims.get("role", String.class));
    }

    public static Date extractExpiration(AuthToken authToken) {
        return extractClaim(authToken, Claims::getExpiration);
    }
}

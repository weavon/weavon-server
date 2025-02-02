package coz.weavon.context.security.domain.service;

import coz.weavon.context.security.domain.model.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.function.Function;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthTokenResolver {

    public static <T> T resolveClaim(AuthToken authToken, Function<Claims, T> claimResolver) {
        final Claims claims = resolveClaims(authToken);
        return claimResolver.apply(claims);
    }

    public static Claims resolveClaims(AuthToken authToken) {
        return Jwts.parser()
                .verifyWith(AuthTokenGenerator.secretKey)
                .build()
                .parseSignedClaims(authToken.getValue())
                .getPayload();
    }

    public static String resolveUsername(AuthToken authToken) {
        return resolveClaim(authToken, Claims::getSubject);
    }

    public static Date resolveExpiration(AuthToken authToken) {
        return resolveClaim(authToken, Claims::getExpiration);
    }

    public static String resolveRole(AuthToken authToken) {
        return resolveClaim(authToken, claims -> claims.get("role", String.class));
    }
}

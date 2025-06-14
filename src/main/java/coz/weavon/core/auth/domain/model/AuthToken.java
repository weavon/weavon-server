package coz.weavon.core.auth.domain.model;

import coz.weavon.core.auth.domain.service.AuthTokenExtractor;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthToken {

    private String accessToken;

    private String refreshToken;

    public static AuthToken of(String accessToken, String refreshToken) {
        return AuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthUser toAuthUser() {
        return AuthTokenExtractor.extractAuthUser(refreshToken);
    }

    public boolean isAccessTokenAlive() {
        try {
            Date expiredDate = AuthTokenExtractor.extractExpiration(accessToken);
            return expiredDate.after(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public boolean isAccessTokenExpired() {
        return !isAccessTokenAlive();
    }

    public boolean isRefreshTokenAlive() {
        try {
            Date expiredDate = AuthTokenExtractor.extractExpiration(refreshToken);
            return expiredDate.after(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }
}

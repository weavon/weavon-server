package coz.weavon.core.auth.domain.model;

import coz.weavon.core.auth.domain.service.AuthTokenExtractor;
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
        return AuthTokenExtractor.extractAuthUser(accessToken);
    }

    public boolean isAccessTokenExpired() {
        Date expiredDate = AuthTokenExtractor.extractExpiration(accessToken);
        return expiredDate.before(new Date());
    }

    public boolean isAccessTokenAlive() {
        Date expiredDate = AuthTokenExtractor.extractExpiration(accessToken);
        return expiredDate.after(new Date());
    }

    public boolean isRefreshTokenAlive() {
        Date expiredDate = AuthTokenExtractor.extractExpiration(refreshToken);
        return expiredDate.after(new Date());
    }
}

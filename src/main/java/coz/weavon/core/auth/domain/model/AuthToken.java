package coz.weavon.core.auth.domain.model;

import coz.weavon.core.auth.domain.service.AuthTokenExtractor;
import coz.weavon.core.auth.domain.service.AuthTokenGenerator;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Date;
import java.util.Objects;
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

    public void refreshAccessToken() {
        if (Objects.isNull(accessToken)) return;

        accessToken = AuthTokenGenerator.generateAccessToken(AuthTokenExtractor.extractAuthUser(refreshToken));
    }

    public boolean shouldRefreshAccessToken() {
        boolean accessTokenExpired = !isAccessTokenAlive();
        boolean hasRefreshToken = Objects.nonNull(refreshToken);

        return accessTokenExpired && hasRefreshToken;
    }

    public boolean isAccessTokenAlive() {
        if (Objects.isNull(accessToken)) {
            return false;
        }

        try {
            Date expiredDate = AuthTokenExtractor.extractExpiration(accessToken);
            return expiredDate.after(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }
}

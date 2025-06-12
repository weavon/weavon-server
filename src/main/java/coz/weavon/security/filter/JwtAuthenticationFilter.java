package coz.weavon.security.filter;

import coz.weavon.core.auth.domain.model.AuthToken;
import coz.weavon.core.auth.domain.model.AuthUser;
import coz.weavon.core.auth.domain.service.AuthTokenGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = extractAccessToken(request.getHeader("Authorization"));
        String refreshToken = extractRefreshToken(request.getCookies());

        if (StringUtils.hasText(accessToken)) {
            processTokenAuthentication(accessToken, refreshToken);
        }

        filterChain.doFilter(request, response);
    }

    private void processTokenAuthentication(String accessToken, String refreshToken) {
        AuthToken authToken = AuthToken.of(accessToken, refreshToken);

        if (authToken.isAccessTokenExpired() && authToken.isRefreshTokenAlive()) {
            String refreshedAccessToken = AuthTokenGenerator.generateAccessToken(authToken.toAuthUser());
            authToken.setAccessToken(refreshedAccessToken);
        }

        if (authToken.isAccessTokenAlive()) {
            AuthUser authUser = authToken.toAuthUser();
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String extractAccessToken(String authorizationHeader) {
        if (Objects.isNull(authorizationHeader) || authorizationHeader.isEmpty()) {
            return null;
        }

        if (authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    private String extractRefreshToken(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if ("REFRESH_TOKEN".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}

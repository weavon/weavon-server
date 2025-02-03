package coz.weavon.context.auth.presentation.filter;

import coz.weavon.context.auth.domain.model.AuthToken;
import coz.weavon.context.auth.domain.model.AuthUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        AuthToken authToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                authToken = AuthToken.of(cookie.getValue());
                break;
            }
        }

        if (ObjectUtils.isEmpty(authToken) || authToken.isExpired()) {
            filterChain.doFilter(request, response);
            return;
        }

        AuthUser authUser = authToken.toAuthUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(authUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}

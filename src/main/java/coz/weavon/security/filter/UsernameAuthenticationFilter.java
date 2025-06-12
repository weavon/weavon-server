package coz.weavon.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import coz.weavon.core.auth.domain.model.AuthToken;
import coz.weavon.core.auth.domain.model.AuthUser;
import coz.weavon.core.shared.presentation.model.response.ErrorResponse;
import coz.weavon.core.shared.presentation.model.response.RestResponse;
import coz.weavon.helper.MessageTranslator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UsernameAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${auth.cookie.secure}")
    private boolean secureCookie;

    @Value("${auth.jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;

    private static final String MSG_AUTH_USER_LOGGED_IN = "message.authentication.user.loggedIn";

    private static final String MSG_AUTH_FAILED = "message.authentication.failed";

    private final MessageTranslator messageTranslator;

    private final AuthenticationManager authenticationManager;

    public UsernameAuthenticationFilter(
            AuthenticationManager authenticationManager, MessageTranslator messageTranslator) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.messageTranslator = messageTranslator;
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        AuthUser user = mapper.readValue(request.getInputStream(), AuthUser.class);
        Authentication token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        AuthUser authUser = (AuthUser) authResult.getPrincipal();
        AuthToken authToken = authUser.toAuthToken();
        response.setHeader("Authorization", "Bearer " + authToken.getAccessToken());

        Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", authToken.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(refreshTokenExpirationMinutes * 60 * 1000);
        refreshTokenCookie.setSecure(secureCookie);

        response.addCookie(refreshTokenCookie);

        log.info(messageTranslator.translate(MSG_AUTH_USER_LOGGED_IN, authUser.getUsername()));
    }

    @Override
    @SneakyThrows
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        RestResponse<ErrorResponse> errorResponse =
                RestResponse.ofAuthError(messageTranslator.translate(MSG_AUTH_FAILED));
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

        log.error(messageTranslator.translate(MSG_AUTH_FAILED));
    }
}

package coz.weavon.context.auth.presentation.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import coz.weavon.common.application.service.MessageTranslator;
import coz.weavon.common.presentation.model.response.ErrorResponse;
import coz.weavon.common.presentation.model.response.RestResponse;
import coz.weavon.context.auth.domain.model.AuthToken;
import coz.weavon.context.auth.domain.model.AuthUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Value("${client.base-url}")
    private String clientBaseUrl;

    @Value("${auth.cookie.secure}")
    private boolean cookieSecure;

    @Value("${auth.cookie.max-age}")
    private int cookieMaxAge;

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
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException {
        AuthUser authUser = (AuthUser) authResult.getPrincipal();
        AuthToken authToken = authUser.toAuthToken();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addCookie(this.generateAuthCookie(authToken));
        response.sendRedirect(clientBaseUrl);

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

    private Cookie generateAuthCookie(AuthToken authToken) {
        Cookie cookie = new Cookie("Authorization", authToken.getValue());

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setMaxAge(cookieMaxAge);

        return cookie;
    }
}

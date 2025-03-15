package coz.weavon.core.auth.presentation.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import coz.weavon.helper.MessageTranslator;
import coz.weavon.common.presentation.model.response.ErrorResponse;
import coz.weavon.common.presentation.model.response.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String MSG_AUTH_FAILED = "message.authentication.failed";

    private final MessageTranslator messageTranslator;

    @Override
    @SneakyThrows
    public void onAuthenticationFailure(
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

package coz.weavon.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import coz.weavon.common.response.ErrorResponse;
import coz.weavon.common.response.RestResponse;
import coz.weavon.constant.Message;
import coz.weavon.helper.MessageTranslator;
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

    private final MessageTranslator messageTranslator;

    @Override
    @SneakyThrows
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        RestResponse<ErrorResponse> errorResponse =
                RestResponse.ofAuthError(messageTranslator.translate(Message.Authentication.AUTHENTICATION_FAILED));
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

        log.error(messageTranslator.translate(Message.Authentication.AUTHENTICATION_FAILED));
    }
}

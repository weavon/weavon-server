package coz.weavon.context.auth.presentation.controller;

import coz.weavon.common.presentation.model.response.RestResponse;
import coz.weavon.context.auth.application.service.AuthService;
import coz.weavon.context.auth.domain.model.AuthUser;
import coz.weavon.context.auth.presentation.model.AuthValidResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/valid")
    public RestResponse<AuthValidResponse> getAuthValid() {
        AuthUser authUser = (AuthUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return RestResponse.of(AuthValidResponse.of(authUser));
    }

    @GetMapping("/username/{username}/existence")
    public RestResponse<Boolean> getExistenceOfUsername(@PathVariable("username") String username) {
        boolean existenceOfUsername = authService.checkUsernameExistence(username);
        return RestResponse.of(existenceOfUsername);
    }
}

package coz.weavon.config;

import coz.weavon.security.filter.JwtAuthenticationFilter;
import coz.weavon.security.filter.UsernameAuthenticationFilter;
import coz.weavon.security.handler.AuthenticationEntryPointHandler;
import coz.weavon.security.handler.OAuthFailureHandler;
import coz.weavon.security.handler.OAuthSuccessHandler;
import coz.weavon.security.service.AuthUserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${client.base-url}")
    private String clientBaseUrl;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UsernameAuthenticationFilter usernameAuthenticationFilter;

    private final AuthUserService authUserService;

    private final OAuthSuccessHandler oAuthSuccessHandler;

    private final OAuthFailureHandler oAuthFailureHandler;

    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(customizer -> customizer.configurationSource(request -> corConfiguration()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.requestMatchers("/login", "/auth/join")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(config -> config.userService(authUserService))
                        .successHandler(oAuthSuccessHandler)
                        .failureHandler(oAuthFailureHandler))
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPointHandler))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilter(usernameAuthenticationFilter)
                .build();
    }

    private CorsConfiguration corConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList(clientBaseUrl));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);
        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

        return configuration;
    }
}

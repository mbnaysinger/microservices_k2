package com.naysinger.product.config;

import com.naysinger.product.helpers.ClientAuthenticationHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    private final ClientAuthenticationHelper authServiceHelper;
    private final ApiKeyFilterConfig config;

    public WebSecurityConfig(ClientAuthenticationHelper authServiceHelper, ApiKeyFilterConfig config) {
        this.authServiceHelper = authServiceHelper;
        this.config = config;
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new ApiKeyFilter(authServiceHelper, config),
                AnonymousAuthenticationFilter.class);

        http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
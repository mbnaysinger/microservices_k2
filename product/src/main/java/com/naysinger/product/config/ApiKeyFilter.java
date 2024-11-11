package com.naysinger.product.config;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.naysinger.product.helpers.ClientAuthenticationHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApiKeyFilter extends OncePerRequestFilter {

    private static final List<Pattern> EXCLUDED_PATH_PATTERNS = List.of(
            Pattern.compile("/swagger-ui(/.*)?"),
            Pattern.compile("/v3/api-docs.*"),
            Pattern.compile("/swagger-ui.html")
    );

    private final ClientAuthenticationHelper authServiceHelper;

    public ApiKeyFilter(ClientAuthenticationHelper authServiceHelper, ApiKeyFilterConfig config) {
        this.authServiceHelper = authServiceHelper;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        System.out.println("Request Path: " + path);
        boolean excluded = EXCLUDED_PATH_PATTERNS.stream().anyMatch(pattern -> pattern.matcher(path).matches());
        System.out.println("Should not filter: " + excluded);
        return excluded;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
            ServletException, IOException {

        String reqApiKey = request.getHeader("Api-Key");
        boolean isApiKeyValid = authServiceHelper.validateApiKey(reqApiKey);

        if (!isApiKeyValid) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid API Key");
            return;
        }

        var authenticationToken = new UsernamePasswordAuthenticationToken(reqApiKey,
                reqApiKey, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
package br.org.fiergs.cosmos.config.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.fiergs.common.dto.ErrorDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomSecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    public CustomSecurityExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        writeCustomResponse(request, response, authException, getStatus(authException));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        writeCustomResponse(request, response, accessDeniedException, HttpStatus.FORBIDDEN);
    }

    private void writeCustomResponse(HttpServletRequest request, HttpServletResponse response,
            Exception authException, HttpStatus status) throws IOException {

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        byte[] body = parse(ErrorDTO.builder()
                .withError(status.getReasonPhrase())
                .withStatus(status.value())
                .withMessage(authException.getMessage())
                .withTimestamp(ZonedDateTime.now())
                .withPath(request.getRequestURI())
                .build()).getBytes(StandardCharsets.UTF_8);

        response.getOutputStream().write(body);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    private HttpStatus getStatus(AuthenticationException authException) {
        if (authException instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException) authException).getError();
            if (error instanceof BearerTokenError) {
                return ((BearerTokenError) error).getHttpStatus();
            }
        }
        return HttpStatus.UNAUTHORIZED;
    }

    private String parse(ErrorDTO dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ocorreu um erro no parse do DTO de erro", e);
        }

    }
}
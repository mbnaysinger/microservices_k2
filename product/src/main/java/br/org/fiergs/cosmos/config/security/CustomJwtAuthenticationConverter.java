package br.org.fiergs.cosmos.config.security;

import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter;

    public CustomJwtAuthenticationConverter() {
        this.grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        this.grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        this.grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return grantedAuthoritiesConverter.convert(jwt);
    }
}

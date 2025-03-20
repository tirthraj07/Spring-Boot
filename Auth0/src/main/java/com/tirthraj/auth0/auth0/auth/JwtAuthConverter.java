package com.tirthraj.auth0.auth0.auth;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final String ROLES_CLAIM = "https://myapi.example.com/claims/roles";
    private static final String EMAIL_CLAIM = "https://myapi.example.com/claims/email";


    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = defaultGrantedAuthoritiesConverter.convert(jwt);

        List<String> roles = jwt.getClaimAsStringList(ROLES_CLAIM);
        if (roles != null) {
            List<GrantedAuthority> roleAuthorities = roles.stream()
                    .map(role -> "ROLE_" + role)
                    .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            authorities.addAll(roleAuthorities);
        }

        System.out.println("ROLES");
        System.out.println(roles);

        String email = jwt.getClaim(EMAIL_CLAIM);
        String name = jwt.getSubject();
        return new JwtAuthenticationToken(jwt, authorities,name);
    }
}

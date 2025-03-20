package com.tirthraj.auth0.auth0.config;

import com.tirthraj.auth0.auth0.auth.JwtAuthConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // disable csrf
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // enable cors
        httpSecurity.cors(Customizer.withDefaults());
        // use token based auth
        httpSecurity.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // jwt auth using auth0
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.
                jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
                ));

        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers("/api/v1/healthcheck/**").permitAll()
                .requestMatchers("/api/v1/public/**").permitAll()
                .requestMatchers("/api/v1/role/tenant/**").hasRole("Tenant")
                .requestMatchers("/api/v1/role/agency/**").hasRole("Agency")
                .requestMatchers("/api/v1/role/landlord/**").hasRole("Landlord")
                .anyRequest().authenticated()
        );

        return httpSecurity.build();
    }

}

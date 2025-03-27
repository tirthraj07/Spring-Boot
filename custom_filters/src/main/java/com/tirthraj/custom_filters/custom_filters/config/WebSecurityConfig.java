package com.tirthraj.custom_filters.custom_filters.config;

import com.tirthraj.custom_filters.custom_filters.filters.CustomHeaderExtractorFilter;
import com.tirthraj.custom_filters.custom_filters.filters.CustomHeaderFilter;
import com.tirthraj.custom_filters.custom_filters.filters.LoggingFilter;
import com.tirthraj.custom_filters.custom_filters.filters.RateLimitingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private LoggingFilter loggingFilter;

    @Autowired
    private CustomHeaderFilter customHeaderFilter;

    @Autowired
    private RateLimitingFilter rateLimitingFilter;

    @Autowired
    private CustomHeaderExtractorFilter customHeaderExtractorFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.authorizeHttpRequests(requests -> requests
                .requestMatchers("/health/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/protected/**").authenticated()
                .anyRequest().authenticated()
        );

        httpSecurity.addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(customHeaderFilter, LoggingFilter.class);
        httpSecurity.addFilterAfter(customHeaderExtractorFilter, CustomHeaderFilter.class);

        httpSecurity.addFilterAfter(rateLimitingFilter, BasicAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.builder().username("user").password(passwordEncoder().encode("user")).roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
